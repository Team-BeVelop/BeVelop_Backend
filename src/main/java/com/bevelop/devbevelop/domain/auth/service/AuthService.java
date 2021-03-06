package com.bevelop.devbevelop.domain.auth.service;

import com.bevelop.devbevelop.domain.user.domain.User;
import com.bevelop.devbevelop.global.common.response.CommonResult;
import com.bevelop.devbevelop.global.config.security.jwt.dto.RegenerateTokenDto;
import com.bevelop.devbevelop.global.config.security.jwt.dto.TokenDto;
import com.bevelop.devbevelop.domain.auth.dto.UserSignUpDto;
import com.bevelop.devbevelop.domain.auth.dto.UserLogInDto;
import org.springframework.http.ResponseEntity;


public interface AuthService {

    /**
     * 유저의 정보로 회원가입
     * @param userSignUpDto 가입할 유저의 정보 Dto
     * @return 가입된 유저 정보
     */
    CommonResult join(UserSignUpDto userSignUpDto);

    CommonResult join(User user);

    /**
     * 유저 정보로 로그인
     * @param userLoginDto 유저의 이메일과 비밀번호
     * @return json web token
     */
    ResponseEntity<TokenDto> login(UserLogInDto userLoginDto);

    ResponseEntity<TokenDto> login(User user);

    ResponseEntity<TokenDto> regenerateToken(RegenerateTokenDto refreshTokenDto);


}
