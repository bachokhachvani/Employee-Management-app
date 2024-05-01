package com.bachokhachvani.employeemanagementapp.domain;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmployeeContactInfoDTO {
    private Integer phone;
    @Email(message = "Email should be valid")
    private String email;
}
