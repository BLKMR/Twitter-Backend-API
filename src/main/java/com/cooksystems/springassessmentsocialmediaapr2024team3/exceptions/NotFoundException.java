package com.cooksystems.springassessmentsocialmediaapr2024team3.exceptions;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String errorMessage) {
        super(errorMessage);
    }

}
