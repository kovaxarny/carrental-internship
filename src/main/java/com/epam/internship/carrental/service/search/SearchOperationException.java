package com.epam.internship.carrental.service.search;

public class SearchOperationException  extends RuntimeException{
    private String exceptionMsg;

    public SearchOperationException(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }
    public String getExceptionMsg(){
        return this.exceptionMsg;
    }
    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }
}
