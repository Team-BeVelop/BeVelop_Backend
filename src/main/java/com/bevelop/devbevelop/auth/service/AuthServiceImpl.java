package com.bevelop.devbevelop.auth.service;

import com.bevelop.devbevelop.common.exception.BaseException;
import com.bevelop.devbevelop.common.exception.ErrorCode;
import com.bevelop.devbevelop.common.handler.CustomException;
import com.bevelop.devbevelop.common.response.CommonResult;
import com.bevelop.devbevelop.common.response.ResponseService;
import com.bevelop.devbevelop.config.security.jwt.JwtTokenProvider;
import com.bevelop.devbevelop.config.security.jwt.dto.RegenerateTokenDto;
import com.bevelop.devbevelop.config.security.jwt.dto.TokenDto;
import com.bevelop.devbevelop.domain.user.domain.User;
import com.bevelop.devbevelop.auth.dto.UserSignUpDto;
import com.bevelop.devbevelop.auth.dto.UserLoginDto;
import com.bevelop.devbevelop.domain.user.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final ResponseService responseService;

    private final PasswordEncoder bCryptPasswordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;

    private final RedisTemplate<String, String> redisTemplate;

    @Value("${jwt.token.refresh-token-expire-length}")
    private long refresh_token_expire_time;

    @Override
    @Transactional
    public CommonResult join(UserSignUpDto userSignUpDto){
        System.out.println("signUpReq = " + userSignUpDto.toString());

        if(userRepository.existsByEmail(userSignUpDto.getEmail())) {
            return responseService.getFailResult(400,"Your Mail already Exist");
        }
        User newUser = userSignUpDto.toUserEntity();
        newUser.hashPassword(bCryptPasswordEncoder);

        User user = userRepository.save(newUser);
        if(!Objects.isNull(user)) {
            // 성공할때도 200을 보낼수도있고 201을 보낼수도 있어서 나중에 변경 필요
            return responseService.getSuccessResult();
        }
        return responseService.getFailResult(400,"Fail to Sign up");
    }

    @Override
    public ResponseEntity<TokenDto> login(UserLoginDto userLoginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginDto.getEmail(),
                            userLoginDto.getPassword()
                    )
            );

            String refresh_token = jwtTokenProvider.generateRefreshToken(authentication);

            TokenDto tokenDto = new TokenDto(jwtTokenProvider.generateAccessToken(authentication), refresh_token);

            // Redis에 저장 - 만료 시간 설정을 통해 자동 삭제 처리
            redisTemplate.opsForValue().set(
                    authentication.getName(),
                    refresh_token,
                    refresh_token_expire_time,
                    TimeUnit.MILLISECONDS
            );

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Authorization", "Bearer " + tokenDto.getAccess_token());
            return new ResponseEntity<>(tokenDto, httpHeaders, HttpStatus.OK);
        } catch (AuthenticationException e) {
            throw new BaseException("Invalid credentials supplied", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<TokenDto> regenerateToken(RegenerateTokenDto refreshTokenDto) {
        String refresh_token = refreshTokenDto.getRefresh_token();
        try {
            // Refresh Token 검증
            if (!jwtTokenProvider.validateRefreshToken(refresh_token)) {
                throw new BaseException("Invalid refresh token supplied", HttpStatus.BAD_REQUEST);
            }

            // Access Token 에서 User email를 가져온다.
            Authentication authentication = jwtTokenProvider.getAuthenticationByRefreshToken(refresh_token);

            // Redis에서 저장된 Refresh Token 값을 가져온다.
            String refreshToken = redisTemplate.opsForValue().get(authentication.getName());
            if(!refreshToken.equals(refresh_token)) {
                throw new BaseException("Refresh Token doesn't match.", HttpStatus.BAD_REQUEST);
            }

            // 토큰 재발행
            String new_refresh_token = jwtTokenProvider.generateRefreshToken(authentication);
            String new_access_token = jwtTokenProvider.generateAccessToken(authentication);
            TokenDto tokenDto = new TokenDto(
                    new_access_token,
                    new_refresh_token
            );

            // RefreshToken Redis에 업데이트
            redisTemplate.opsForValue().set(
                    authentication.getName(),
                    new_refresh_token,
                    refresh_token_expire_time,
                    TimeUnit.MILLISECONDS
            );

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Authorization", "Bearer " + tokenDto.getAccess_token());

            return new ResponseEntity<>(tokenDto, httpHeaders, HttpStatus.OK);
        } catch (ExpiredJwtException e) {
            throw new CustomException(ErrorCode.JWT_REFRESH_TOKEN_EXPIRED);
        }
    }
}

