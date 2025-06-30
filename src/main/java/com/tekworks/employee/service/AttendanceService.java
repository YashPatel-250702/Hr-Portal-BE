package com.tekworks.employee.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tekworks.employee.dto.AllEmployeeAttendanceDaySummaryDTO;
import com.tekworks.employee.dto.AttendanceDaySummaryDTO;
import com.tekworks.employee.entity.AttendenceLogs;
import com.tekworks.employee.entity.Employee;
import com.tekworks.employee.enums.EventType;
import com.tekworks.employee.exception.CustomException;
import com.tekworks.employee.repository.AttendenceLogsRepository;
import com.tekworks.employee.repository.EmployeeRepository;

@Service
public class AttendanceService {

    @Autowired
    private AttendenceLogsRepository attendanceRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    public AttendenceLogs checkIn(String employeeId) throws CustomException {
            return saveLog(employeeId, EventType.CHECK_IN);
    }

    
    public AttendenceLogs checkOut(String employeeId) throws CustomException {
        return saveLog(employeeId, EventType.CHECK_OUT);
    }
    
    
    private AttendenceLogs saveLog(String employeeId, EventType type) throws CustomException {
        Employee emp = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND.value(), "Employee not found"));

        LocalDate today = LocalDate.now();

        Optional<AttendenceLogs> todayLogOpt = attendanceRepo
                .findTopByEmployeeAndCreatedAtOrderByTimestampDesc(emp, today);

        if (todayLogOpt.isPresent() && todayLogOpt.get().getEventType().equals(type)) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "Employee already " + type + " today");
        }

        AttendenceLogs log = new AttendenceLogs();
        log.setEmployee(emp);
        log.setEventType(type);
        log.setTimestamp(LocalDateTime.now());
        log.setCreatedAt(today);

        return attendanceRepo.save(log);
    }

    public List<AttendanceDaySummaryDTO> getEmployeeFullAttendance(String empId) throws CustomException {
    	
     employeeRepo.findById(empId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND.value(),"Employee not found"));
    	
    	
        List<AttendenceLogs> logs = attendanceRepo.findByEmployee(empId);

        System.out.println(logs);
        
        Map<LocalDate, List<AttendenceLogs>> groupedLogs = logs.stream()
            .collect(Collectors.groupingBy(log -> log.getTimestamp().toLocalDate()));

        List<AttendanceDaySummaryDTO> summaryList = new ArrayList<>();

        for (Map.Entry<LocalDate, List<AttendenceLogs>> entry : groupedLogs.entrySet()) {
            LocalDate date = entry.getKey();
            List<AttendenceLogs> dayLogs = entry.getValue();
            dayLogs.sort(Comparator.comparing(AttendenceLogs::getTimestamp));

            List<AttendanceDaySummaryDTO.AttendanceEventDTO> events = new ArrayList<>();
            LocalDateTime lastCheckIn = null;
            Duration total = Duration.ZERO;

            for (AttendenceLogs log : dayLogs) {
                events.add(new AttendanceDaySummaryDTO.AttendanceEventDTO(
                    log.getEventType().name(), log.getTimestamp()
                ));

                if (log.getEventType() == EventType.CHECK_IN) {
                    lastCheckIn = log.getTimestamp();
                } else if (log.getEventType() == EventType.CHECK_OUT && lastCheckIn != null) {
                    total = total.plus(Duration.between(lastCheckIn, log.getTimestamp()));
                    lastCheckIn = null;
                }
            }
            long totalMinutes = total.toMinutes();
            long hours = totalMinutes / 60;
            long minutes = totalMinutes % 60;

            String formattedWorkedHours = hours + "h " + minutes + "min";

            summaryList.add(new AttendanceDaySummaryDTO(date, events, formattedWorkedHours));
        }

        return summaryList;
    }
    
    
    
    public List<AllEmployeeAttendanceDaySummaryDTO> getAttendanceForAllEmployeesByDate(LocalDate date) throws CustomException {
        List<AttendenceLogs> logs = attendanceRepo.findByDate(date);

        if (logs.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "No attendance records found for the given date");
        }

        
        System.out.println("Employee logs"+logs);
        Map<String, List<AttendenceLogs>> groupedByEmployee = logs.stream()
        	    .collect(Collectors.groupingBy(log -> log.getEmployee().getEmployeeId()));


        List<AllEmployeeAttendanceDaySummaryDTO> summaryList = new ArrayList<>();

        for (Map.Entry<String, List<AttendenceLogs>> entry : groupedByEmployee.entrySet()) {
            String empId = entry.getKey();
            List<AttendenceLogs> empLogs = entry.getValue();
            empLogs.sort(Comparator.comparing(AttendenceLogs::getTimestamp));

            List<AttendanceDaySummaryDTO.AttendanceEventDTO> events = new ArrayList<>();
            LocalDateTime lastCheckIn = null;
            Duration total = Duration.ZERO;

            for (AttendenceLogs log : empLogs) {
                events.add(new AttendanceDaySummaryDTO.AttendanceEventDTO(
                    log.getEventType().name(), log.getTimestamp()
                ));

                if (log.getEventType() == EventType.CHECK_IN) {
                    lastCheckIn = log.getTimestamp();
                } else if (log.getEventType() == EventType.CHECK_OUT && lastCheckIn != null) {
                    total = total.plus(Duration.between(lastCheckIn, log.getTimestamp()));
                    lastCheckIn = null;
                }
            }

            long totalMinutes = total.toMinutes();
            long hours = totalMinutes / 60;
            long minutes = totalMinutes % 60;

            String formattedWorkedHours = hours + "h " + minutes + "min";

            summaryList.add(new AllEmployeeAttendanceDaySummaryDTO(date,empId, events, formattedWorkedHours));
        }

        return summaryList;
    }

    
   
}
