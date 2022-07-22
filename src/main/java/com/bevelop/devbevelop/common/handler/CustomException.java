package com.bevelop.devbevelop.common.handler;

import com.bevelop.devbevelop.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private ErrorCode error;

    public CustomException(ErrorCode e) {
        super(e.getMessage());
        this.error = e;
    }
}