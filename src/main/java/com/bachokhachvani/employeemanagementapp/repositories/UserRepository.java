package com.bachokhachvani.employeemanagementapp.repositories;

import com.bachokhachvani.employeemanagementapp.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Integer> {
    Optional<UserModel> findByUsername(String name);
}
