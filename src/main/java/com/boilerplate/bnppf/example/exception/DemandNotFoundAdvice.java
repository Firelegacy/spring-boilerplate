package com.boilerplate.bnppf.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DemandNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(DemandNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String DemandNotFoundHandler(DemandNotFoundException demandNotFoundException) {
        return demandNotFoundException.getMessage();
    }
}
