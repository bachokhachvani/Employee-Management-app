package com.bachokhachvani.employeemanagementapp.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDTO {

    @NotBlank(message = "Username must not be empty")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters long")
    private String username;

    @NotBlank(message = "Password must not be empty")
    @Size(min = 6, max = 60, message = "Password must be between 3 and 60 characters long")
    private String password;

    private Role roleName;
}
