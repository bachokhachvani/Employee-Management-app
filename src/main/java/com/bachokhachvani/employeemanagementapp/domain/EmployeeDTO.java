package com.bachokhachvani.employeemanagementapp.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.sql.Date;


@Data
public class EmployeeDTO {

    @NotBlank(message = "Username must not be empty")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters long")
    private String name;

    @Min(value = 100000, message = "Phone number must be at least 6 digits long")
    private Integer phone;

    private Date birthday;

    @Email(message = "Email should be valid")
    private String email;
    private Date hireDate;
    private Double salary;
    private String department;


    private String position;

    @Email(message = "Email should be valid")
    private String managerEmail;
    private String managerName;
}
