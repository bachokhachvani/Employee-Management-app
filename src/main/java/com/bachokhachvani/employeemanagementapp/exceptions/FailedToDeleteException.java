package com.bachokhachvani.employeemanagementapp.exceptions;

public class FailedToDeleteException extends RuntimeException {
    public FailedToDeleteException(String message,Exception e) {
        super(message,e);
    }
}

