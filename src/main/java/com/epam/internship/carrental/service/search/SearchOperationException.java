package com.epam.internship.carrental.service.search;

public class SearchOperationException  extends RuntimeException{
    private final String exceptionMsg;

    public SearchOperationException(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }
    public String getExceptionMsg(){
        return this.exceptionMsg;
    }
}
