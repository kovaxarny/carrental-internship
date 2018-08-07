package com.epam.internship.carrental.service.user;

public class UserAlreadyExistsException  extends RuntimeException{
    private final String exceptionMsg;

    public UserAlreadyExistsException(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }
    public String getExceptionMsg(){
        return this.exceptionMsg;
    }
}