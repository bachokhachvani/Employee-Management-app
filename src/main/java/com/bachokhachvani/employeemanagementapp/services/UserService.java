package com.bachokhachvani.employeemanagementapp.services;

import com.bachokhachvani.employeemanagementapp.domain.Role;
import com.bachokhachvani.employeemanagementapp.exceptions.UserRoleNotFoundException;
import com.bachokhachvani.employeemanagementapp.models.RoleModel;
import com.bachokhachvani.employeemanagementapp.models.UserModel;
import com.bachokhachvani.employeemanagementapp.repositories.RoleRepository;
import com.bachokhachvani.employeemanagementapp.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;


    public void addUser(UserModel user, Role roleName){
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        RoleModel role;
        role = roleRepository.findByName(Objects.requireNonNullElse(roleName, Role.EMPLOYEE))
                .orElseThrow(() -> new UserRoleNotFoundException("User Role not found"));
        user.setPassword(encodedPassword);
        user.setRole(role);
        userRepository.save(user);
    }

}
