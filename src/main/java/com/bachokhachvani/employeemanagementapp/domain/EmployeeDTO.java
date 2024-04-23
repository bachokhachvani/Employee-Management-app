package com.bachokhachvani.employeemanagementapp.domain;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;


@Getter
@Setter
public class EmployeeDTO {
    private String name;
    private Integer phone;
    private Date birthday;
    private String email;
    private Date hireDate;
    private Double salary;
    private String department;
    private String position;
    private String managerEmail;
    private String managerName;
}
