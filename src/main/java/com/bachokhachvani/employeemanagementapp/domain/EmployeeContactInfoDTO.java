package com.bachokhachvani.employeemanagementapp.domain;

import jakarta.validation.constraints.Email;
import lombok.Data;


@Data
public class EmployeeContactInfoDTO {
    private Integer phone;
    @Email(message = "Email should be valid")
    private String email;
}
