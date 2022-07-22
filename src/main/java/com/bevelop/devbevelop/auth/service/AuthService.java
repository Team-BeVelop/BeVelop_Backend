package com.bevelop.devbevelop.auth.service;

import com.bevelop.devbevelop.common.response.CommonResult;
import com.bevelop.devbevelop.config.security.jwt.dto.RegenerateTokenDto;
import com.bevelop.devbevelop.config.security.jwt.dto.TokenDto;
import com.bevelop.devbevelop.auth.dto.UserSignUpDto;
import com.bevelop.devbevelop.auth.dto.UserLoginDto;
import org.springframework.http.ResponseEntity;


public interface AuthService {

    /**
     * 유저의 정보로 회원가입
     * @param userSignUpDto 가입할 유저의 정보 Dto
     * @return 가입된 유저 정보
     */
    CommonResult join(UserSignUpDto userSignUpDto);

    /**
     * 유저 정보로 로그인
     * @param userLoginDto 유저의 이메일과 비밀번호
     * @return json web token
     */
    ResponseEntity<TokenDto> login(UserLoginDto userLoginDto);

    ResponseEntity<TokenDto> regenerateToken(RegenerateTokenDto refreshTokenDto);


}
