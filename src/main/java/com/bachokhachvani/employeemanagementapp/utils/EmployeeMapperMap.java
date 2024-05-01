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
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;


@Mapper(componentModel = "spring")
public abstract class EmployeeMapperMap {

    @Autowired
    protected PositionRepository positionRepository;
    @Autowired
    protected DepartmentRepository departmentRepository;
    @Autowired
    protected EmployeeRepository employeeRepository;
    @Autowired
    protected UserRepository userRepository;


    @Mapping(source = "department.name", target = "department", defaultExpression = "java(null)")
    @Mapping(source = "position.name", target = "position", defaultExpression = "java(null)")
    @Mapping(source = "manager.email", target = "managerEmail", defaultExpression = "java(null)")
    @Mapping(source = "manager.name", target = "managerName", defaultExpression = "java(null)")
    public abstract EmployeeDTO toDTO(EmployeeModel employeeModel);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "department", source = "department", qualifiedByName = "validateDepartment")
    @Mapping(target = "position", source = "position", qualifiedByName = "validatePosition")
    @Mapping(target = "manager", source = "managerEmail", qualifiedByName = "validateManager",defaultExpression = "java(null)")
    public abstract EmployeeModel fromDTO(EmployeeDTO employeeDTO);

    @Named("validatePosition")
    public PositionModel validatePosition(String positionName) {
        if (positionName != null) {
            return positionRepository.findByName(positionName)
                    .orElseThrow(() -> new ResourceNotFoundException("Position not found"));
        }
        return null;
    }
    @Named("validateDepartment")
    public DepartmentModel validateDepartment(String departmentName) {
        if (departmentName != null) {
            return departmentRepository.findByName(departmentName)
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        }
        return null;
    }
    @Named("validateManager")
    public EmployeeModel validateManager(String managerEmail) {
//        if (managerEmail != null) {
//            System.out.println("managerEmail: " + managerEmail);
//            return employeeRepository.findByEmail(managerEmail)
//                    .orElseThrow(() -> new RuntimeException("Manager not found"));
//        }
//        return null;
        if (managerEmail == null || managerEmail.trim().isEmpty()) {
            return null;
        }
        return employeeRepository.findByEmail(managerEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Manager not found"));
    }
}
