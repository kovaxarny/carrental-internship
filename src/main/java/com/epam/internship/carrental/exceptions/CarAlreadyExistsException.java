package com.epam.internship.carrental.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.FORBIDDEN, reason="Already exists")
public class CarAlreadyExistsException extends RuntimeException{
}
