package com.cooksystems.springassessmentsocialmediaapr2024team3.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(Exception ex){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        if (ex instanceof BadRequestException) {
            BadRequestException badRequestException = new BadRequestException(
                    ex.getMessage()
            );

            return new ResponseEntity<>(badRequestException, badRequest);
        }

        return null;

        }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
        // Log the exception
        System.err.println("Not Found Exception: " + ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found Exception occurred");
    }

    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<String> handleNotAuthorizedException(NotAuthorizedException ex) {
        // Log the exception
        System.err.println("Not Authorized Exception: " + ex.getMessage());

        // Customize the error message or response
        return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
    }

}
