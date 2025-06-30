package com.tekworks.employee.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tekworks.employee.entity.AttendenceLogs;
import com.tekworks.employee.entity.Employee;

public interface AttendenceLogsRepository extends JpaRepository<AttendenceLogs, Long>{

	@Query(value = "SELECT * FROM attendence_logs WHERE employee_id  = :empId ORDER BY timestamp ASC", nativeQuery = true)
	List<AttendenceLogs> findByEmployee(@Param("empId") String empId);
	
	Optional<AttendenceLogs> findTopByEmployeeAndCreatedAtOrderByTimestampDesc(Employee employee, LocalDate date);

	
	@Query("SELECT a FROM AttendenceLogs a WHERE createdAt = :date")
	List<AttendenceLogs> findByDate(@Param("date") LocalDate date);
	
	Optional<AttendenceLogs> findTopByEmployeeAndTimestampBetweenOrderByTimestampDesc(
		    Employee employee, LocalDateTime start, LocalDateTime end
		);

}
