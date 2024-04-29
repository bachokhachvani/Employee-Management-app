package com.bachokhachvani.employeemanagementapp.exceptions;

public class UserRoleNotFoundException extends RuntimeException {
    public UserRoleNotFoundException(String msg) {
        super(msg);
    }
}
