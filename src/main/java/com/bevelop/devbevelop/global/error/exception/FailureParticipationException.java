package com.bevelop.devbevelop.global.error.exception;

public class FailureParticipationException extends RuntimeException {

    public FailureParticipationException() {
        super("스터디 가입이 불가능합니다");
    }
}
