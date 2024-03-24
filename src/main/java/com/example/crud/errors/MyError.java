package com.example.crud.errors;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyError {
    private int statusCode;
    private String message;

    public MyError() {
    }

    public MyError(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}

