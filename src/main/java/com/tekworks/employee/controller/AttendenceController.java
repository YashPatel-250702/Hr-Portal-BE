package com.tekworks.employee.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tekworks.employee.dto.AllEmployeeAttendanceDaySummaryDTO;
import com.tekworks.employee.dto.AttendanceDaySummaryDTO;
import com.tekworks.employee.exception.CustomException;
import com.tekworks.employee.service.AttendanceService;

@RestController
@RequestMapping("/employee/attandence")
public class AttendenceController {

	@Autowired
	private AttendanceService attendanceService;
	
	@PostMapping("/checkin/{empId}")
    public ResponseEntity<?> checkIn(@PathVariable String empId) {
        try {
        	return ResponseEntity.ok(attendanceService.checkIn(empId));
		} catch (CustomException e) {
	        return ResponseEntity.status(e.getStatus()).body(e.getMessage());

	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Internal server error while adding checkin logs");
	    }
    }

    @PostMapping("/checkout/{empId}")
    public ResponseEntity<?> checkOut(@PathVariable String empId) {
    	try {
        	return ResponseEntity.ok(attendanceService.checkOut(empId));
		} catch (CustomException e) {
	        return ResponseEntity.status(e.getStatus()).body(e.getMessage());

	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Internal server error while adding checkout logs");
	    }
    }
    
    @GetMapping("/logs/{empId}")
    public ResponseEntity<?> getLogs(@PathVariable String empId) {
       
        try {
        	List<AttendanceDaySummaryDTO> employeeFullAttendance = attendanceService.getEmployeeFullAttendance(empId);
        	 
        	 if(employeeFullAttendance==null) {
        		 return ResponseEntity.status(HttpStatus.NOT_FOUND)
                         .body("No Logs Found");
        	 }
        	 return ResponseEntity.ok(employeeFullAttendance);
		} catch (CustomException e) {
	        return ResponseEntity.status(e.getStatus()).body(e.getMessage());

	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Internal server error while getting logs");
	    }
    }
    
    @GetMapping("/logsByDate/{date}")
    public ResponseEntity<?> getLogs(@PathVariable LocalDate date) {
       
        try {
        	List<AllEmployeeAttendanceDaySummaryDTO> employeeFullAttendance = attendanceService.getAttendanceForAllEmployeesByDate(date);
        	 
        	 if(employeeFullAttendance==null) {
        		 return ResponseEntity.status(HttpStatus.NOT_FOUND)
                         .body("No Logs Found");
        	 }
        	 return ResponseEntity.ok(employeeFullAttendance);
		} catch (CustomException e) {
	        return ResponseEntity.status(e.getStatus()).body(e.getMessage());

	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Internal server error while getting logs");
	    }
    }
}
