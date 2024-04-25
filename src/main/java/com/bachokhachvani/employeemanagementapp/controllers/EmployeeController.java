package com.bachokhachvani.employeemanagementapp.controllers;

import com.bachokhachvani.employeemanagementapp.domain.EmployeeContactInfoDTO;
import com.bachokhachvani.employeemanagementapp.domain.EmployeeDTO;
import com.bachokhachvani.employeemanagementapp.models.EmployeeModel;
import com.bachokhachvani.employeemanagementapp.services.EmployeeService;
import com.bachokhachvani.employeemanagementapp.services.UserService;
import com.bachokhachvani.employeemanagementapp.utils.EmployeeMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeMapper employeeMapper;
    private EmployeeService employeeService;
    private UserService userService;


    @GetMapping("/employee")
    public List<EmployeeModel> allEmployees() {
        return employeeService.allEmployees();
    }

    @PostMapping("/add-details")
    public ResponseEntity<?> addEmployee(@RequestBody EmployeeDTO employeeDTO) {
        try {
            employeeService.addEmployee(employeeDTO);
            return ResponseEntity.ok(employeeDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/my-profile")
    public EmployeeDTO getMyProfile() {
        return employeeService.getCurrentEmployeeDetails();
    }

    @PatchMapping("/my-profile")
    public ResponseEntity<String> updateMyProfile(@RequestBody EmployeeContactInfoDTO employeeContactInfoDTO) {
        employeeService.updateContactInfo(employeeContactInfoDTO);
        return ResponseEntity.ok("Updated");
    }

    @PatchMapping("/update-employee/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<String> updateEmployee(@PathVariable Integer id, @RequestBody EmployeeDTO employeeDTO) {
        employeeService.updateEmployee(employeeDTO, id);
        return ResponseEntity.ok("Updated");
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping("/employee/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(employeeService.findEmployeeById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @DeleteMapping("/employee/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Integer id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.ok("Deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
