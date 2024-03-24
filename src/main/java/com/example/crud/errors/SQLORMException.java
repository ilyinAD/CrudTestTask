package com.example.crud.errors;

public class SQLORMException extends Exception {
    public SQLORMException(String errorMessage) {
        super(errorMessage);
    }
}