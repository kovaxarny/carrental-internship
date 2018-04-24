package com.epam.internship.carrental.service.alert;

public class UserAlreadyExistsException  extends RuntimeException{
    private String exceptionMsg;

    public UserAlreadyExistsException(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }
    public String getExceptionMsg(){
        return this.exceptionMsg;
    }
    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }
}