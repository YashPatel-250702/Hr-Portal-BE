package com.tekworks.employee.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tekworks.employee.entity.Employee;
import com.tekworks.employee.exception.CustomException;
import com.tekworks.employee.repository.EmployeeRepository;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	public void addEmployee(Employee emp) throws CustomException {
		Optional<Employee> employee = employeeRepository.findById(emp.getEmployeeId());
		if(employee.isPresent()) {
			throw new CustomException(HttpStatus.BAD_REQUEST.value(),"Employee Already Present With employeeId");
		}
		
		emp.setCreatedAt(LocalDateTime.now());
		 employeeRepository.save(emp);
	}
	
	public Employee getEmployeeById(String id) throws CustomException {
		return employeeRepository.findById(id).
				orElseThrow(()->new CustomException(HttpStatus.NOT_FOUND.value(), "Employee Not found with id"));
	}
	
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
	}

	public Employee updateEmployeeData(String empId, Employee emp) throws CustomException {
		 employeeRepository.findById(empId).
		orElseThrow(()->new CustomException(HttpStatus.NOT_FOUND.value(), "Employee Not found with id"));
	    
		Employee save = employeeRepository.save(emp);
		return save;
	}
	
	public void deleteEmployee(String empId) {
		employeeRepository.deleteById(empId);
	}
}
