package com.example.meetingmanager.repository;

import com.example.meetingmanager.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {}