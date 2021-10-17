package com.tsystem.logisticsbe.exception;

public class ApiException extends RuntimeException {

    private static final String EXCEPTION_DESCRIPTION = "Unexpected error occurred in spring-boot-service";

    private final Integer status;
    private final String description;

    public ApiException(Integer status, String description) {
        super(description);
        this.status = status;
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return getDescription();
    }

    public String getDescription() {
        return description.isEmpty() ? EXCEPTION_DESCRIPTION : description;
    }
}