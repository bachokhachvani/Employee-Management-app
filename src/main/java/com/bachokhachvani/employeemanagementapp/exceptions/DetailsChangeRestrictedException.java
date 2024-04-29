package com.bachokhachvani.employeemanagementapp.exceptions;

public class DetailsChangeRestrictedException extends RuntimeException{
    public DetailsChangeRestrictedException(String message){
        super(message);
    }
}
