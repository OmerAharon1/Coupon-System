package com.jb.CouponSystem.advice;

import com.jb.CouponSystem.exceptions.CouponSystemException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {CouponSystemException.class})
    public ErrDetails handleError(Exception e) {
        return new ErrDetails("", e.getMessage());

    }
}

