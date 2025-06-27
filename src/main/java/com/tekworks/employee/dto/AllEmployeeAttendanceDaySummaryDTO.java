package com.tekworks.employee.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.tekworks.employee.dto.AttendanceDaySummaryDTO.AttendanceEventDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllEmployeeAttendanceDaySummaryDTO {
    private LocalDate date;
    private String employeeId;
    private List<AttendanceEventDTO> events;
    private String totalHours;

  

}

