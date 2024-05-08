package com.bachokhachvani.employeemanagementapp.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import lombok.Data;


@Data
public class EmployeeContactInfoDTO {
    @Min(value = 100000, message = "Phone number must be at least 6 digits long")
    private Integer phone;
    @Email(message = "Email should be valid")
    private String email;
}
