package com.tekworks.employee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tekworks.employee.entity.Admin;
import com.tekworks.employee.exception.CustomException;
import com.tekworks.employee.repository.AdminRepository;

@Service
public class AdminService {
	
	@Autowired
	private AdminRepository adminRepository;

	public Admin RegisterEmployee(Admin admin) throws CustomException {
		Admin admin1 = adminRepository.findByEmail(admin.getEmail());
	    if (admin1 != null) {
	        throw new CustomException(HttpStatus.BAD_REQUEST.value(), "Email Already Registered");
	    }
	    return adminRepository.save(admin);
	}
	
	public Admin loginEmployee(String email,String password) throws CustomException {
		Admin adm=adminRepository.findByEmail(email);
		if(adm==null) {
			throw new CustomException(HttpStatus.BAD_REQUEST.value(),"No Account found with email");
		}
		Admin admin=adminRepository.findByEmailAndPassword(email,password);
		return admin;
	}
}
	
	
	
