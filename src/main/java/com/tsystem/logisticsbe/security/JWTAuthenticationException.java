package com.tsystem.logisticsbe.security;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

@Getter
public class JWTAuthenticationException extends AuthenticationException {

    private HttpStatus status;

    public JWTAuthenticationException(String msg, HttpStatus status) {
        super(msg);
        this.status = status;
    }

    public JWTAuthenticationException(String msg) {
        super(msg);
    }
}
