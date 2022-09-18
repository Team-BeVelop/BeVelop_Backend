package com.bevelop.devbevelop.domain.auth.service;

import com.bevelop.devbevelop.domain.auth.dto.*;
import com.bevelop.devbevelop.domain.user.domain.User;
import com.bevelop.devbevelop.global.common.response.CommonResult;
import com.bevelop.devbevelop.global.config.security.jwt.dto.RegenerateTokenDto;
import com.bevelop.devbevelop.global.config.security.jwt.dto.TokenDto;
import io.swagger.models.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;


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
    ResponseEntity<?> login(UserLogInDto userLoginDto);

    ResponseEntity<TokenDto> login(User user);


    CommonResult logout(UserLogOutDto userLogOutDto);

    CommonResult remove(UserDetails userDetails, UserWithdrawalDto userWithdrawalDto);

    CommonResult update(Long id, UserDetails userDetails, UserUpdateDto userUpdateDto);

    ResponseEntity<TokenDto> regenerateToken(RegenerateTokenDto refreshTokenDto);

    CommonResult validateDuplicateMember(String email);
}
