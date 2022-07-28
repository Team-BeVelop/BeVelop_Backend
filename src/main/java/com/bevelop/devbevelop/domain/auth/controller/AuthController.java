package com.bevelop.devbevelop.domain.auth.controller;


import com.bevelop.devbevelop.domain.auth.dto.KakaoProfile;
import com.bevelop.devbevelop.domain.auth.dto.KakaoSignUpDto;
import com.bevelop.devbevelop.domain.auth.dto.UserLogInDto;
import com.bevelop.devbevelop.domain.auth.dto.UserSignUpDto;
import com.bevelop.devbevelop.domain.auth.service.KakaoService;
import com.bevelop.devbevelop.domain.user.domain.User;
import com.bevelop.devbevelop.domain.user.repository.UserRepository;
import com.bevelop.devbevelop.global.common.response.CommonResult;
import com.bevelop.devbevelop.global.config.security.jwt.dto.RegenerateTokenDto;
import com.bevelop.devbevelop.global.config.security.jwt.dto.TokenDto;
import com.bevelop.devbevelop.domain.auth.service.AuthService;
import com.bevelop.devbevelop.global.error.ErrorCode;
import com.bevelop.devbevelop.global.error.exception.CustomException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = {"1. Auth Controller"})
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/auth")
@Controller
public class AuthController {

    private final AuthService authService;
    private final KakaoService kakaoService;
    private UserRepository userRepository;

    @ApiOperation(value = "가입", notes = "회원 가입")
    @PostMapping("/signup")
    public CommonResult signUp(@Validated UserSignUpDto userSignUpDto) { return authService.join(userSignUpDto);}

    @ApiOperation(value = "로그인", notes = "회원 로그인")
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@Validated UserLogInDto userLoginDto) { return authService.login(userLoginDto); }

    @ApiOperation(value = "토큰 재발급", notes = "회원 토큰 재발급")
    @PostMapping("/regenerateToken")
    public ResponseEntity<TokenDto> regenerateToken(@Validated RegenerateTokenDto refreshTokenDto) {
        return authService.regenerateToken(refreshTokenDto);
    }

    @ApiOperation(value = "소셜 계정 가입", notes = "소셜 계정 회원가입을 한다.")
    @PostMapping(value = "/signup/{provider}")
    public CommonResult signupProvider(@ApiParam(value = "서비스 제공자 provider", required = true, defaultValue = "kakao") @PathVariable String provider,
                                 @ApiParam(value = "소셜 authentication code", required = true) @RequestParam String code,
                                 @Valid KakaoSignUpDto kakaoSignUpDto) {

        KakaoProfile profile = kakaoService.execKakaoLogin(code);
        User user = User.builder()
                .name(kakaoSignUpDto.getName())
                .provider(provider)
                .socialId(profile.getId())
                .job(kakaoSignUpDto.getJob())
                .interests(kakaoSignUpDto.getInterests())
                .build();
        return authService.join(user);
//        return responseService.getSuccessResult();
//        return "redirect:webauthcallback://success?customToken="+jwtTokenProvider.createToken(user.getId(), user.getRole());
    }

    @ApiOperation(value = "소셜 로그인", notes = "소셜 회원 로그인을 한다.")
    @PostMapping(value = "/login/{provider}")
    public ResponseEntity<TokenDto> loginByProvider(
            @ApiParam(value = "서비스 제공자 provider", required = true, defaultValue = "kakao") @PathVariable String provider,
            @ApiParam(value = "소셜 authentication code", required = true) @RequestParam String code) {

        KakaoProfile profile = kakaoService.execKakaoLogin(code);
        User user = userRepository.findBySocialIdAndProvider(profile.getId(), provider).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        return authService.login(user);
//        return responseService.getSingleResult(jwtTokenProvider.createToken(user.getId(), user.getRole()));
//        return "redirect:webauthcallback://success?customToken="+jwtTokenProvider.createToken(user.ge tId(), user.getRole());
    }

}
