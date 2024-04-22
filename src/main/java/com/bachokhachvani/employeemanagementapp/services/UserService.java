package com.bachokhachvani.employeemanagementapp.services;

import com.bachokhachvani.employeemanagementapp.models.RoleModel;
import com.bachokhachvani.employeemanagementapp.models.UserModel;
import com.bachokhachvani.employeemanagementapp.repositories.RoleRepository;
import com.bachokhachvani.employeemanagementapp.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    public void addUser(UserModel user, String roleName) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        RoleModel role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setPassword(encodedPassword);
        user.setRole(role);
        userRepository.save(user);
    }

    public Optional<UserModel> getUsersByName(String name) {
        return userRepository.findByUsername(name);
    }

}
