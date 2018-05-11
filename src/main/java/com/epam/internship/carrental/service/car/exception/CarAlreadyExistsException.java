package com.epam.internship.carrental.service.car.exception;

public class CarAlreadyExistsException extends RuntimeException{
    private final String exceptionMsg;

    public CarAlreadyExistsException(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }
    public String getExceptionMsg(){
        return this.exceptionMsg;
    }
}
