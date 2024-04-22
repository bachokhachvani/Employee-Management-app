package com.bachokhachvani.employeemanagementapp.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDTO {
    private String username;
    private String password;
    private String roleName;
}
