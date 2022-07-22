package com.bevelop.devbevelop.domain.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserNotValidException extends RuntimeException{
    public UserNotValidException(String msg) {
        super(msg);
    }
}
