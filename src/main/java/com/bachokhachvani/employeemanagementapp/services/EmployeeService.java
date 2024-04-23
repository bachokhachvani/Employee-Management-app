package com.bachokhachvani.employeemanagementapp.services;

import com.bachokhachvani.employeemanagementapp.domain.EmployeeContactInfoDTO;
import com.bachokhachvani.employeemanagementapp.domain.EmployeeDTO;
import com.bachokhachvani.employeemanagementapp.models.EmployeeModel;
import com.bachokhachvani.employeemanagementapp.repositories.EmployeeRepository;
import com.bachokhachvani.employeemanagementapp.repositories.UserRepository;
import com.bachokhachvani.employeemanagementapp.utils.EmployeeMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final UserRepository userRepository;

    public List<EmployeeModel> allEmployees() {
        return employeeRepository.findAll();
    }

    public void addEmployee(EmployeeDTO employeeDTO) {
        if (employeeRepository.findById(employeeMapper.currentUserID()).isEmpty()) {
            var employee = employeeMapper.fromDTO(employeeDTO, employeeMapper.currentUserID(), new EmployeeModel());
            employeeRepository.save(employee);
        }
        throw new RuntimeException("you can't change your details");
    }

    public void updateContactInfo(EmployeeContactInfoDTO contactInfo) {
        var id = employeeMapper.currentUserID();
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

    public void updateEmployee(EmployeeDTO employeeDTO, Integer employeeID) {
        var employee = employeeRepository.findById(employeeID).orElseThrow(() -> new RuntimeException("Employee not found"));
        employee = employeeMapper.fromDTO(employeeDTO, employeeID, employee);
        employeeRepository.save(employee);

    }

    public EmployeeDTO findEmployeeById(Integer employeeID) {
        var employee = employeeRepository.findById(employeeID).orElseThrow(() -> new RuntimeException("Employee not found"));
        var employeeDTO = employeeMapper.toDTO(employee);
        if (employee.getManager().getName().isEmpty()) {
            employeeDTO.setManagerName(null);
        } else {
            employeeDTO.setManagerName(employee.getManager().getName());
        }
        return employeeDTO;
    }

    @Transactional
    public void deleteEmployee(Integer employeeID) throws DataIntegrityViolationException {
        try {
            List<EmployeeModel> subordinates = employeeRepository.findByManagerId(employeeID).orElseThrow(() -> new RuntimeException("Employees not found"));
            for (EmployeeModel subordinate : subordinates) {
                subordinate.setManager(null);
                employeeRepository.save(subordinate);
            }
            employeeRepository.deleteById(employeeID);
            userRepository.deleteById(employeeID);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete user and employee with ID: " + employeeID, e);
        }
    }

}
