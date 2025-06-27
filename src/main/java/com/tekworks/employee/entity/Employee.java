package com.tekworks.employee.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
	
	@Id
	@NotBlank(message = "Please Provide EmployeeId")
	private String employeeId;

	@NotBlank(message = "Please Provide Employee Name")
	private String name;

	@Email(message = "Please provide a valid email")
	@NotBlank(message = "Please Provide Email")
	private String email;

	@Pattern(regexp = "\\d{10}", message = "Please provide valid 10-digit mobile number")
	@NotBlank(message = "Please Provide Mobile Number")
	private String mobileNumber;
	
	@NotNull(message = "Please Provide Salary")
	private Double salary;
	
	@NotNull(message = "Please Provide Joining Date")
	private LocalDate joiningDate;
	
	@NotBlank(message = "Please Provide Employee position")
	private String position;
	
	@NotBlank(message="Please Provide Employee Address")
	private String address;
	
	@NotBlank(message = "Please Provide Employee Department name")
	private String departmentName;
	
	@Column(nullable = true)
	private String workingHours;
	
	private LocalDateTime createdAt;
	

}
