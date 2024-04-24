package com.cooksystems.springassessmentsocialmediaapr2024team3.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class NotAuthorizedException extends RuntimeException {
    private HttpStatus status;

    public NotAuthorizedException(HttpStatus status) {
        this.status = status;
    }

    public NotAuthorizedException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
