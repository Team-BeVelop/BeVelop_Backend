package com.bevelop.devbevelop.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /*
    * 400 BAD_REQUEST : 잘못된 요청
    * 401 UNAUTHORIZED : 인증되지 않은 사용자
    * 404 NOT_FOUND : Resource 를 찾을 수 없음
    * 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재
    */

    /* 유효기간이 지난 경우*/
    JWT_ACCESS_TOKEN_EXPIRED(BAD_REQUEST, "TOKEN-0001", "Access Token Expired"),
    JWT_REFRESH_TOKEN_EXPIRED(BAD_REQUEST, "TOKEN-0002", "리프레시 토큰이 유효 기간이 지났습니다"),

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    INVALID_AUTH_TOKEN(UNAUTHORIZED, "TOKEN-0003","권한 정보가 없는 토큰입니다"),
    INVALID_AUTH_REFRESH_TOKEN(UNAUTHORIZED, "TOKEN-0004","권한 정보가 없는 리프레시 토큰입니다"),


    // Member
    MEMBER_NOT_FOUND(NOT_FOUND,"MEMBER-0001", "일치하는 회원 정보를 찾을 수 없습니다"),
    MEMBER_EXISTS(CONFLICT, "MEMBER-0002", "이미 존재하는 회원 정보입니다"),
    MEMBER_SIGNUP_FAIL(BAD_REQUEST, "MEMBER-0003", "가입 실패"),

    //Project
    PROJECT_NOT_FOUND(NOT_FOUND, "PROJECT-0001", "존재하지 않는 프로젝트입니다"),
    PROJECT_EXISTS(CONFLICT, "PROJECT-0002", "이미 존재하는 프로젝트입니다"),

    //Team
    TEAM_NOT_FOUND(NOT_FOUND, "TEAM-0001", "존재하지 않는 팀입니다"),
    TEAM_EXISTS(CONFLICT, "TEAM-0002", "이미 존재하는 팀입니다."),

    //Communication
    COMMUNICATION_ERROR(BAD_REQUEST, "COMM-0001", "Communication error"),

    /* 400 BAD_REQUEST : 잘못된 요청 */
//    INVALID_REFRESH_TOKEN(BAD_REQUEST, "REFRESH-TOKEN-001","리프레시 토큰이 유효하지 않습니다"),
    MISMATCH_REFRESH_TOKEN(BAD_REQUEST,"REFRESH-TOKEN-002", "리프레시 토큰의 유저 정보가 일치하지 않습니다"),
    CANNOT_FOLLOW_MYSELF(BAD_REQUEST, "REFRESH-TOKEN-003","자기 자신은 팔로우 할 수 없습니다"),

    // Login
    LOGIN_ERROR(BAD_REQUEST, "LOGIN-001" , "Invalid Credentials Supplied"),

    // Logout
    LOGOUT_ERROR(BAD_REQUEST, "LOGOUT-001", "잘못된 요청입니다"),

    WRONG_PASSWORD(BAD_REQUEST, "PASS-001", "비밀번호가 잘못되었습니다"),

    OWNER_CANT_LEAVE(BAD_REQUEST, "STUDY-001", "스터디장은 스터디를 탈퇴할 수 없습니다"),

    USER_NOT_IN_STUDY(BAD_REQUEST, "STUDY-002", "스터디에 참여하지 않는 회원입니다"),

    OWNER_AUTH(BAD_REQUEST, "STUDY-003", "스터디 방장만이 스터디를 수정할 수 있습니다."),

    STUDY_NOT_FOUND(BAD_REQUEST, "STUDY-004", "존재하지 않는 스터디입니다");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
//

//    UNAUTHORIZED_MEMBER(UNAUTHORIZED, "현재 내 계정 정보가 존재하지 않습니다"),

//
//    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
//    MEMBER_NOT_FOUND(NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다"),
//    REFRESH_TOKEN_NOT_FOUND(NOT_FOUND, "로그아웃 된 사용자입니다"),
//    NOT_FOLLOW(NOT_FOUND, "팔로우 중이지 않습니다"),
//
//    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
//    DUPLICATE_RESOURCE(CONFLICT, "데이터가 이미 존재합니다"),
//
//    ;