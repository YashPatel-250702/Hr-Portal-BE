package com.tekworks.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tekworks.employee.dto.LoginDTO;
import com.tekworks.employee.entity.Admin;
import com.tekworks.employee.exception.CustomException;
import com.tekworks.employee.service.AdminService;

@RestController
@RequestMapping("/auth")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@PostMapping("/register")
	public ResponseEntity<?>register(@RequestBody Admin employee){
		try {
			Admin emp=adminService.RegisterEmployee(employee);
			if(emp==null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User Registration failed");
			}
			return ResponseEntity.status(HttpStatus.OK).body("User Registered SuccessFully");
		}catch (CustomException e) {
			return ResponseEntity.status(e.getStatus()).body(e.getMessage());
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error while registering");
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginDTO login){
		try {
			Admin employee=adminService.loginEmployee(login.getEmail(), login.getPassword());
			if(employee==null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Inavlid Credentials");
			}
			return ResponseEntity.status(HttpStatus.OK).body("User Registered SuccessFully");
		} catch (CustomException e) {
			return ResponseEntity.status(e.getStatus()).body(e.getMessage());
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error while login");
		}
	}

}
