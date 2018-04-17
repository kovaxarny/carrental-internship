package com.epam.internship.carrental.service.reservation.exception;

public class ReservationRepositoryException extends RuntimeException{
    private String exceptionMsg;

    public ReservationRepositoryException(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }
    public String getExceptionMsg(){
        return this.exceptionMsg;
    }
    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }
}
