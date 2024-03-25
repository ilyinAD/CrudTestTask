package com.example.crud.errors;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MyError {
    private int statusCode;
    private String message;
    private Date time;

    public MyError() {
    }

    public MyError(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
        this.time = new Date(System.currentTimeMillis());
    }
}

