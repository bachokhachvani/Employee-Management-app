package com.bachokhachvani.employeemanagementapp.utils;

import com.bachokhachvani.employeemanagementapp.domain.EmployeeDTO;
import com.bachokhachvani.employeemanagementapp.exceptions.ResourceNotFoundException;
import com.bachokhachvani.employeemanagementapp.models.DepartmentModel;
import com.bachokhachvani.employeemanagementapp.models.EmployeeModel;
import com.bachokhachvani.employeemanagementapp.models.PositionModel;
import com.bachokhachvani.employeemanagementapp.repositories.DepartmentRepository;
import com.bachokhachvani.employeemanagementapp.repositories.EmployeeRepository;
import com.bachokhachvani.employeemanagementapp.repositories.PositionRepository;
import com.bachokhachvani.employeemanagementapp.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor
@Component
public class EmployeeMapper {
    private UserRepository userRepository;
    public int currentUserID() {
        return userRepository.findByUsername(SecurityContextHolder
                        .getContext().getAuthentication().getName())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found")).getUserId();
    }

}

