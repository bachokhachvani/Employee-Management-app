package com.bachokhachvani.employeemanagementapp.repositories;

import com.bachokhachvani.employeemanagementapp.models.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleModel, Integer> {

    Optional<RoleModel> findByName(String name);

}
