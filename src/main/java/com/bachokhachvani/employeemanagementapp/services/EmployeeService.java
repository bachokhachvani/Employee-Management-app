package com.bachokhachvani.employeemanagementapp.services;

import com.bachokhachvani.employeemanagementapp.domain.EmployeeContactInfoDTO;
import com.bachokhachvani.employeemanagementapp.domain.EmployeeDTO;
import com.bachokhachvani.employeemanagementapp.models.EmployeeModel;
import com.bachokhachvani.employeemanagementapp.repositories.EmployeeRepository;
import com.bachokhachvani.employeemanagementapp.repositories.UserRepository;
import com.bachokhachvani.employeemanagementapp.utils.EmployeeMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public List<EmployeeModel> allEmployees() {
        return employeeRepository.findAll();
    }

    public void addEmployee(EmployeeDTO employeeDTO) {
        var employee=employeeMapper.fromDTO(employeeDTO);
        employeeRepository.save(employee);
    }

    public void updateContactInfo(EmployeeContactInfoDTO contactInfo) {
        var id=employeeMapper.currentUserID();
        EmployeeModel employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (contactInfo.getPhone() != null) {
            employee.setPhone(contactInfo.getPhone());
        }
        if (contactInfo.getEmail() != null) {
            employee.setEmail(contactInfo.getEmail());
        }

        employeeRepository.save(employee);
    }

}
