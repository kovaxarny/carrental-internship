package com.epam.internship.carrental.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such RentedCar")
public class RentedCarNotFoundException extends RuntimeException {
}
