package com.bachokhachvani.employeemanagementapp.services;

import com.bachokhachvani.employeemanagementapp.models.DepartmentModel;
import com.bachokhachvani.employeemanagementapp.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository DepartmentRepository;


    public DepartmentService(DepartmentRepository departmentRepository) {
        DepartmentRepository = departmentRepository;
    }

    public List<DepartmentModel> getAllDepartment(){
        return DepartmentRepository.findAll();
    }



}
