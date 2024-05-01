package com.bachokhachvani.employeemanagementapp.services;

import com.bachokhachvani.employeemanagementapp.domain.EmployeeContactInfoDTO;
import com.bachokhachvani.employeemanagementapp.domain.EmployeeDTO;
import com.bachokhachvani.employeemanagementapp.exceptions.ActionForbiddenException;
import com.bachokhachvani.employeemanagementapp.exceptions.DetailsChangeRestrictedException;
import com.bachokhachvani.employeemanagementapp.exceptions.EmployeeNotFoundException;
import com.bachokhachvani.employeemanagementapp.exceptions.FailedToDeleteException;
import com.bachokhachvani.employeemanagementapp.models.EmployeeModel;
import com.bachokhachvani.employeemanagementapp.repositories.EmployeeRepository;
import com.bachokhachvani.employeemanagementapp.repositories.UserRepository;
import com.bachokhachvani.employeemanagementapp.utils.EmployeeMapperMap;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final EmployeeMapperMap employeeMapperMap;

    public List<EmployeeModel> allEmployees(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return employeeRepository.findAll(pageRequest).getContent();
    }

    public void addEmployee(EmployeeDTO employeeDTO) {
        if (employeeRepository.findById(currentUserID()).isEmpty()) {
            EmployeeModel employee = employeeMapperMap.fromDTO(employeeDTO);
            employee.setId(currentUserID());
            employeeRepository.save(employee);
        } else {
            throw new DetailsChangeRestrictedException("you can't change your details");
        }
    }

    public void updateContactInfo(EmployeeContactInfoDTO contactInfo) {
        var id = currentUserID();
        EmployeeModel employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        if (contactInfo.getPhone() != null) {
            employee.setPhone(contactInfo.getPhone());
        }
        if (contactInfo.getEmail() != null) {
            employee.setEmail(contactInfo.getEmail());
        }

        employeeRepository.save(employee);
    }

    public void updateEmployee(EmployeeDTO employeeDTO, Integer employeeID) {
        var employee = employeeRepository.findById(employeeID).orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
        employee = employeeMapperMap.fromDTO(employeeDTO);
        employee.setId(employeeID);
        if (Objects.equals(currentUserID(), employeeID)) {
            throw new DetailsChangeRestrictedException("You can't change your details");
        }
        employeeRepository.save(employee);

    }

    public EmployeeDTO findEmployeeById(Integer employeeID) {
        var employee = employeeRepository.findById(employeeID).orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
        var employeeDTO = employeeMapperMap.toDTO(employee);
        if (employee.getManager() == null) {
            employeeDTO.setManagerName(null);
        } else {
            employeeDTO.setManagerName(employee.getManager().getName());
        }
        return employeeDTO;
    }

    @Transactional
    public void deleteEmployee(Integer employeeID) {
        if (currentUserID() == employeeID) {
            throw new ActionForbiddenException("You can't delete yourself!");
        }
        try {
            List<EmployeeModel> subordinates = employeeRepository.findByManagerId(employeeID).orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
            for (EmployeeModel subordinate : subordinates) {
                subordinate.setManager(null);
                employeeRepository.save(subordinate);
            }
            employeeRepository.deleteById(employeeID);
            userRepository.deleteById(employeeID);
        } catch (Exception e) {
            throw new FailedToDeleteException("Failed to delete user and employee with ID: " + employeeID, e);
        }
    }

    public EmployeeDTO getCurrentEmployeeDetails() {
        var employee = employeeRepository.findById(currentUserID()).orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
        var employeeDTO = employeeMapperMap.toDTO(employee);
        if (employee.getManager() == null) {
            employeeDTO.setManagerName(null);
        } else {
            employeeDTO.setManagerName(employee.getManager().getName());
        }
        return employeeDTO;
    }

    public int currentUserID(){
        return userRepository.findByUsername(SecurityContextHolder
                        .getContext().getAuthentication().getName())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found")).getUserId();
    }

}
