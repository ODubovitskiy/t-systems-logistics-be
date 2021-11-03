package com.tsystem.logisticsbe.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {

    private final HttpStatus status;
    private final String description;

    public ApiException(HttpStatus status, String description) {
        super(description);
        this.status = status;
        this.description = description;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return getDescription();
    }

    public String getDescription() {
        return description;
    }
}