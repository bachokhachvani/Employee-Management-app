package com.bachokhachvani.employeemanagementapp.repositories;


import com.bachokhachvani.employeemanagementapp.models.EmployeeModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface EmployeeRepository extends JpaRepository<EmployeeModel,Integer> {
  Optional<EmployeeModel> findByEmail(String email);
  Optional<List<EmployeeModel>> findByManagerId(int managerId);
}
