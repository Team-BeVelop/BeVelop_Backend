package com.bevelop.devbevelop.common.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    // Member
    NOT_FOUND_MEMBER(HttpStatus.BAD_REQUEST,"MEMBER-0001", "Member not found"),
    EXISTING_MEMBER(HttpStatus.BAD_REQUEST, "MEMBER-0002", "Member that already exists"),

    // Post
    NOT_FOUND_POST(HttpStatus.BAD_REQUEST, "POST-0001", "Post not found"),

//    // File
//    AWS_S3_UPLOAD_FAIL(HttpStatus.BAD_REQUEST, "FILE-0001", "AWS S3 File upload fail"),
//    AWS_S3_UPLOAD_VALID(HttpStatus.BAD_REQUEST, "FILE-0002", "File validation"),
//
//    // Role
//    NOT_HAVE_PERMISSION(HttpStatus.BAD_REQUEST, "ROLE-0001", "You do not have permission"),
//    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "ROLE-0002", "Unauthorized"),
//    FORBIDDEN(HttpStatus.FORBIDDEN, "ROLE-0003", "Forbidden"),

    //JWT
    JWT_ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "TOKEN-0001", "Access token has expired"),
    JWT_REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "TOKEN-0002", "Refresh token has expired");


    HttpStatus status;
    String code;
    String message;

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}