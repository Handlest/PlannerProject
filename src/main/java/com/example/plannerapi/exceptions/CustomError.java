package com.example.plannerapi.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class CustomError {
    private int status;
    private String message;
    private List<Map<String, String>> errors;

    public CustomError(int status, String message, List<Map<String, String>> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }
}