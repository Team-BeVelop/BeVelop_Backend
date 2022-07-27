//package com.bevelop.devbevelop.global.common.response;
//
//
//import lombok.Builder;
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.http.HttpStatus;
//
//@Getter
//@Setter
//public class ResponseVO<T> {
//
//    private HttpStatus status;
//
//    private String message;
//
//    private Object response;
//
//    private String code;
//
//
//    /**
//     * RESPONSE BUILDER
//     */
//    @Builder
//    public ResponseVO(HttpStatus status, String message, String code, Object response) {
//        this.status = status;
//        this.message = message;
//        this.code = code;
//        this.response = response;
//    }
//}