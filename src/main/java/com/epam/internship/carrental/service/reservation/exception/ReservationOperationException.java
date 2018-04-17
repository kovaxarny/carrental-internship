package com.epam.internship.carrental.service.reservation.exception;

public class ReservationOperationException extends RuntimeException{
    private String exceptionMsg;

    public ReservationOperationException(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }
    public String getExceptionMsg(){
        return this.exceptionMsg;
    }
    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }
}
