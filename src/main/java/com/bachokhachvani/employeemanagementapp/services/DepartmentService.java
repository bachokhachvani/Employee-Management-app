package com.bachokhachvani.employeemanagementapp.services;

import com.bachokhachvani.employeemanagementapp.models.DepartmentModel;
import com.bachokhachvani.employeemanagementapp.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    private final DepartmentRepository DepartmentRepository;


    public DepartmentService(DepartmentRepository departmentRepository) {
        DepartmentRepository = departmentRepository;
    }

    public Optional<DepartmentModel> getDepartmentByName(String department){
        return DepartmentRepository.findByName(department);
    }



}
