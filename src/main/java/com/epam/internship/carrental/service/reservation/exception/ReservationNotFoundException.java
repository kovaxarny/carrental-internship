package com.epam.internship.carrental.service.reservation.exception;

public class ReservationNotFoundException extends RuntimeException {
    private final String exceptionMsg;

    public ReservationNotFoundException(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }
    public String getExceptionMsg(){
        return this.exceptionMsg;
    }
}
