package com.bachokhachvani.employeemanagementapp.repositories;

import com.bachokhachvani.employeemanagementapp.models.DepartmentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface DepartmentRepository extends JpaRepository<DepartmentModel, Integer> {
    Optional<DepartmentModel> findByName(String name);
}
