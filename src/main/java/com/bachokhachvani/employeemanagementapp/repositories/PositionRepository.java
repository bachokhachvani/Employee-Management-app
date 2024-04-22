package com.bachokhachvani.employeemanagementapp.repositories;

import com.bachokhachvani.employeemanagementapp.models.PositionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PositionRepository extends JpaRepository<PositionModel, Integer> {
    Optional<PositionModel> findByName(String name);
}
