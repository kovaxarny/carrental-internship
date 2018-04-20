package com.epam.internship.carrental.service.car.exception;

public class CarOperationException extends RuntimeException{
    private String exceptionMsg;

    public CarOperationException(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }
    public String getExceptionMsg(){
        return this.exceptionMsg;
    }
    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }
}
