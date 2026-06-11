package com.workintech.s18d1.exceptions;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<BurgerErrorResponse> handleException(BurgerException exception){
        log.error("Burger error exception occured! Details: ", exception);
        BurgerErrorResponse errorResponse = new BurgerErrorResponse(exception.getMessage());
        return new ResponseEntity<>(errorResponse, exception.getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<BurgerErrorResponse> handleException(Exception exec){
        log.error("Exception occured! Details: ", exec);
        BurgerErrorResponse errorResponse = new BurgerErrorResponse(exec.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
