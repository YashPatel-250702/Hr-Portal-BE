package com.tekworks.employee.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tekworks.employee.entity.AttendenceLogs;
import com.tekworks.employee.entity.Employee;

public interface AttendenceLogsRepository extends JpaRepository<AttendenceLogs, Long>{

	@Query(value = "SELECT * FROM attendence_logs WHERE employee_id  = :empId ORDER BY timestamp ASC", nativeQuery = true)
	List<AttendenceLogs> findByEmployee(@Param("empId") String empId);
	AttendenceLogs findTopByEmployeeOrderByTimestampDesc(Employee employee);
	@Query("SELECT a FROM AttendenceLogs a WHERE createdAt = :date")
	List<AttendenceLogs> findByDate(@Param("date") LocalDate date);

}
