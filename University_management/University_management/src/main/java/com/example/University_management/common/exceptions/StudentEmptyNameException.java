package com.example.University_management.common.exceptions;

public class StudentEmptyNameException extends RuntimeException{
    public StudentEmptyNameException(String message) {
        // log
        super(message);
    }
}
