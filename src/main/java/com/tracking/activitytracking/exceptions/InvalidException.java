package com.tracking.activitytracking.exceptions;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class InvalidException extends RuntimeException{
    private  String message;
    private final LocalDateTime time = LocalDateTime.now();
    private HttpStatus status;

    public InvalidException(String message) {
        this.message = message;
    }

    public InvalidException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
