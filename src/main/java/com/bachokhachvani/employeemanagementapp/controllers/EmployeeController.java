package com.bachokhachvani.employeemanagementapp.controllers;

import com.bachokhachvani.employeemanagementapp.domain.EmployeeContactInfoDTO;
import com.bachokhachvani.employeemanagementapp.domain.EmployeeDTO;
import com.bachokhachvani.employeemanagementapp.models.EmployeeModel;
import com.bachokhachvani.employeemanagementapp.models.UserModel;
import com.bachokhachvani.employeemanagementapp.services.EmployeeService;
import com.bachokhachvani.employeemanagementapp.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class EmployeeController {
    private EmployeeService employeeService;
    private UserService userService;


    @GetMapping("/employee")
    public List<EmployeeModel> allEmployees() {
        return employeeService.allEmployees();
    }

    @PostMapping("/add-details")
    public ResponseEntity<EmployeeDTO> addEmployee(@RequestBody EmployeeDTO employeeDTO) {
        employeeService.addEmployee(employeeDTO);
        return ResponseEntity.ok(employeeDTO);
    }

    @GetMapping("/my-profile")
    public UserModel getMyProfile() {
        return userService.getUsersByName(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PatchMapping("/my-profile")
    public ResponseEntity<String> updateMyProfile(@RequestBody EmployeeContactInfoDTO employeeContactInfoDTO) {
        employeeService.updateContactInfo(employeeContactInfoDTO);
        return ResponseEntity.ok("Updated");
    }

}
