package com.tekworks.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tekworks.employee.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String>{

}
