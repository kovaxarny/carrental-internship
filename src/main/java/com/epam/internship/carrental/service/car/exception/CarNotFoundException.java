package com.epam.internship.carrental.service.car.exception;

public class CarNotFoundException extends RuntimeException{
    private final String exceptionMsg;

    public CarNotFoundException(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }
    public String getExceptionMsg(){
        return this.exceptionMsg;
    }
}
