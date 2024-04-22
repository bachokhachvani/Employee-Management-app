package com.bachokhachvani.employeemanagementapp.repositories;


import com.bachokhachvani.employeemanagementapp.models.EmployeeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface EmployeeRepository extends JpaRepository<EmployeeModel,Integer> {
}
