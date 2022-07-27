package com.bevelop.devbevelop.domain.auth.service;

import com.bevelop.devbevelop.domain.auth.dto.UserLogInDto;
import com.bevelop.devbevelop.global.common.response.CommonResult;
import com.bevelop.devbevelop.global.common.response.ResponseService;
import com.bevelop.devbevelop.global.config.security.jwt.JwtTokenProvider;
import com.bevelop.devbevelop.global.config.security.jwt.dto.RegenerateTokenDto;
import com.bevelop.devbevelop.global.config.security.jwt.dto.TokenDto;
import com.bevelop.devbevelop.domain.auth.dto.UserSignUpDto;
import com.bevelop.devbevelop.domain.user.domain.User;
import com.bevelop.devbevelop.domain.user.repository.UserRepository;
import com.bevelop.devbevelop.global.error.exception.CustomException;
import com.bevelop.devbevelop.global.error.ErrorCode;
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
    public CommonResult join(UserSignUpDto userSignUpDto) throws CustomException{
        System.out.println("signUpReq = " + userSignUpDto.toString());

        User newUser = userSignUpDto.toUserEntity();
        newUser.hashPassword(bCryptPasswordEncoder);

        validateDuplicateMember(newUser);

        User user = userRepository.save(newUser);
        if(Objects.isNull(user)) throw new CustomException(ErrorCode.MEMBER_SIGNUP_FAIL);

        // 성공할때도 200을 보낼수도있고 201을 보낼수도 있어서 나중에 변경 필요
        return responseService.getSuccessResult();

//        return responseService.getFailResult(400,"Fail to Sign up");
    }

    @Override
    @Transactional
    public CommonResult join(User user) throws CustomException{

        validateDuplicateMember(user);

        User neswUser = userRepository.save(user);
        if(Objects.isNull(user)) throw new CustomException(ErrorCode.MEMBER_SIGNUP_FAIL);

        // 성공할때도 200을 보낼수도있고 201을 보낼수도 있어서 나중에 변경 필요
        return responseService.getSuccessResult();

//        return responseService.getFailResult(400,"Fail to Sign up");
    }

    private void validateDuplicateMember(User user) {
        Optional<User> findUser = userRepository.findByEmail(user.getEmail());
        if(findUser.isPresent()) throw new CustomException(ErrorCode.MEMBER_EXISTS);
    }


    @Override
    public ResponseEntity<TokenDto> login(UserLogInDto userLoginDto) {
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
//            throw new BaseException("Invalid credentials supplied", HttpStatus.BAD_REQUEST);
            throw new CustomException(ErrorCode.LOGIN_ERROR);
        }
    }

    @Override
    public ResponseEntity<TokenDto> login(User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getSocialId(),
                            user.getProvider()
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
//            throw new BaseException("Invalid credentials supplied", HttpStatus.BAD_REQUEST);
            throw new CustomException(ErrorCode.LOGIN_ERROR);
        }
    }

    @Override
    public ResponseEntity<TokenDto> regenerateToken(RegenerateTokenDto refreshTokenDto) {
        String refresh_token = refreshTokenDto.getRefresh_token();
        try {
            // Refresh Token 검증
            if (!jwtTokenProvider.validateRefreshToken(refresh_token)) {
                throw new CustomException(ErrorCode.JWT_REFRESH_TOKEN_EXPIRED);
            }

            // Access Token 에서 User email를 가져온다.
            Authentication authentication = jwtTokenProvider.getAuthenticationByRefreshToken(refresh_token);

            // Redis에서 저장된 Refresh Token 값을 가져온다.
            String refreshToken = redisTemplate.opsForValue().get(authentication.getName());
            if(!refreshToken.equals(refresh_token)) {
                throw new CustomException(ErrorCode.MISMATCH_REFRESH_TOKEN);
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

