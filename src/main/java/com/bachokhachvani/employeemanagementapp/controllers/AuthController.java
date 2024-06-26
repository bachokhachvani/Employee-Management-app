package com.bachokhachvani.employeemanagementapp.controllers;

import com.bachokhachvani.employeemanagementapp.domain.AuthResponseDTO;
import com.bachokhachvani.employeemanagementapp.domain.LoginDTO;
import com.bachokhachvani.employeemanagementapp.domain.Role;
import com.bachokhachvani.employeemanagementapp.domain.UserDTO;
import com.bachokhachvani.employeemanagementapp.models.UserModel;
import com.bachokhachvani.employeemanagementapp.security.TokenGenerator;
import com.bachokhachvani.employeemanagementapp.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController {

    private UserService userService;
    private AuthenticationManager authenticationManager;
    private TokenGenerator tokenGenerator;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserDTO user) {
        UserModel userModel = new UserModel();
        userModel.setUsername(user.getUsername());
        userModel.setPassword(user.getPassword());

        userService.addUser(userModel, Role.EMPLOYEE);
        return ResponseEntity.ok("User Created Successfully!");
    }

    @PostMapping("/add-admin")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<String> registerAdmin(@Valid @RequestBody UserDTO user) {
        UserModel userModel = new UserModel();
        userModel.setUsername(user.getUsername());
        userModel.setPassword(user.getPassword());
        userService.addUser(userModel, user.getRoleName());
        return ResponseEntity.ok("User Created Successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO login) {

        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenGenerator.generateToken(authentication);
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }

}
