package com.tekworks.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tekworks.employee.entity.Employee;
import com.tekworks.employee.exception.CustomException;
import com.tekworks.employee.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;

	@PostMapping("/addEmployee")
	public ResponseEntity<?> addEmployeeData(@Valid @RequestBody Employee employee) {
		try {
			employeeService.addEmployee(employee);
			return ResponseEntity.status(HttpStatus.OK).body("Employee Added Successfully");

		} catch (CustomException e) {
			return ResponseEntity.status(e.getStatus()).body(e.getMessage());

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Internal server error while adding employee");
		}
	}

	@GetMapping("/getAllEmployees")
	public ResponseEntity<?> getAllEmployees() {
		try {
			List<Employee> allEmployees = employeeService.getAllEmployees();
			return ResponseEntity.status(HttpStatus.OK).body(allEmployees);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Internal server error while getting employee");
		}
	}

	@GetMapping("/getEmployeeById/{employeeId}")
	public ResponseEntity<?> getEmployeeById(@PathVariable String employeeId) {
		try {
			Employee emp = employeeService.getEmployeeById(employeeId);
			return ResponseEntity.status(HttpStatus.OK).body(emp);
		} catch (CustomException e) {
			return ResponseEntity.status(e.getStatus()).body(e.getMessage());

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Internal server error while getting employee");
		}
	}

	@PutMapping("/updateEmployee/{empId}")
	public ResponseEntity<?> updateEmployeeData(@Valid @RequestBody Employee employee, @PathVariable String empId) {
		try {
			employeeService.updateEmployeeData(empId, employee);
			return ResponseEntity.status(HttpStatus.OK).body("Employee Updated Successfully");

		} catch (CustomException e) {
			return ResponseEntity.status(e.getStatus()).body(e.getMessage());

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Internal server error while updating employee");
		}
	}

	@DeleteMapping("/deleteEmployee/{empId}")
	public ResponseEntity<?> deleteEmployeeById(@PathVariable String empId) {
		try {
			employeeService.deleteEmployee(empId);
			return ResponseEntity.status(HttpStatus.OK).body("Employee Deleted Successfully");

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Internal server error while deleting employee");
		}
	}

}
