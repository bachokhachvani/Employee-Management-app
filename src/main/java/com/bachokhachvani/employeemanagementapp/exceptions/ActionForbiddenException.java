package com.bachokhachvani.employeemanagementapp.exceptions;

public class ActionForbiddenException extends RuntimeException {
    public ActionForbiddenException(String msg) {
        super(msg);
    }
}
