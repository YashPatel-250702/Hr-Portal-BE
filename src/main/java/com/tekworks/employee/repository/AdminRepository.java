package com.tekworks.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tekworks.employee.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

	Admin findByEmail(String email);
	Admin findByEmailAndPassword(String email,String password);
}
