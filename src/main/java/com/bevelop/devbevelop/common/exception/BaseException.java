package com.bevelop.devbevelop.common.exception;

import org.springframework.http.HttpStatus;

public class BaseException extends RuntimeException {

    private final String message;
    private final HttpStatus httpStatus;

    public BaseException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}