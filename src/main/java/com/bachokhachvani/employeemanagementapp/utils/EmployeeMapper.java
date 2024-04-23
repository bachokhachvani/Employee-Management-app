package com.bachokhachvani.employeemanagementapp.utils;

import com.bachokhachvani.employeemanagementapp.domain.EmployeeDTO;
import com.bachokhachvani.employeemanagementapp.models.DepartmentModel;
import com.bachokhachvani.employeemanagementapp.models.EmployeeModel;
import com.bachokhachvani.employeemanagementapp.models.PositionModel;
import com.bachokhachvani.employeemanagementapp.repositories.DepartmentRepository;
import com.bachokhachvani.employeemanagementapp.repositories.EmployeeRepository;
import com.bachokhachvani.employeemanagementapp.repositories.PositionRepository;
import com.bachokhachvani.employeemanagementapp.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor
@Component
public class EmployeeMapper {

    private PositionRepository positionRepository;
    private DepartmentRepository departmentRepository;
    private EmployeeRepository employeeRepository;
    private UserRepository userRepository;

    public int currentUserID() {
        return userRepository.findByUsername(SecurityContextHolder
                        .getContext().getAuthentication().getName())
                .orElseThrow(() -> new RuntimeException("User Not Found")).getUserId();
    }

    public EmployeeDTO toDTO(EmployeeModel employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setName(employee.getName());
        dto.setPhone(employee.getPhone());
        dto.setBirthday(employee.getBirthday());
        dto.setEmail(employee.getEmail());
        dto.setHireDate(employee.getHireDate());
        dto.setSalary(employee.getSalary());
        dto.setDepartment(employee.getDepartment() != null ? employee.getDepartment().getName() : null);
        dto.setPosition(employee.getPosition() != null ? employee.getPosition().getName() : null);
        dto.setManagerEmail(employee.getManager() != null ? employee.getManager().getEmail() : null);
        return dto;
    }

    public EmployeeModel fromDTO(EmployeeDTO dto, Integer id, EmployeeModel employee) {
//        employee.setName(dto.getName());
//        employee.setPhone(dto.getPhone());
//        employee.setBirthday(dto.getBirthday());
//        employee.setEmail(dto.getEmail());
//        employee.setHireDate(dto.getHireDate());
//        employee.setSalary(dto.getSalary());
        if (dto.getName() != null) {
            employee.setName(dto.getName());
        }
        if (dto.getPhone() != null) {
            employee.setPhone(dto.getPhone());
        }
        if (dto.getBirthday() != null) {
            employee.setBirthday(dto.getBirthday());
        }
        if (dto.getEmail() != null) {
            employee.setEmail(dto.getEmail());
        }
        if (dto.getHireDate() != null) {
            employee.setHireDate(dto.getHireDate());
        }
        if (dto.getSalary() != null) {
            employee.setSalary(dto.getSalary());
        }

        employee.setId(id);

        if (dto.getDepartment() != null) {
            Optional<DepartmentModel> department = departmentRepository.findByName(dto.getDepartment());
            employee.setDepartment(department.orElseThrow(() -> new RuntimeException("Department not found")));
        }
        if (dto.getPosition() != null) {
            Optional<PositionModel> position = positionRepository.findByName(dto.getPosition());
            employee.setPosition(position.orElseThrow(() -> new RuntimeException("Position not found")));
        }
        if (dto.getManagerEmail() != null) {
            Optional<EmployeeModel> manager = employeeRepository.findByEmail(dto.getManagerEmail());
            employee.setManager(manager.orElseThrow(() -> new RuntimeException("Manager not found")));
        }
        return employee;
    }
}

