package com.bevelop.devbevelop.domain.auth.controller;


import com.bevelop.devbevelop.domain.auth.dto.UserLogInDto;
import com.bevelop.devbevelop.domain.auth.dto.UserSignUpDto;
import com.bevelop.devbevelop.domain.auth.service.KakaoService;
import com.bevelop.devbevelop.global.common.response.CommonResult;
import com.bevelop.devbevelop.global.config.security.jwt.dto.RegenerateTokenDto;
import com.bevelop.devbevelop.global.config.security.jwt.dto.TokenDto;
import com.bevelop.devbevelop.domain.auth.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(value = "Auth")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/auth")
public class AuthController {

    private final AuthService authService;
    private final KakaoService kakaoService;

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

//    @ApiOperation(value = "소셜 계정 가입", notes = "소셜 계정 회원가입을 한다.")
//    @PostMapping(value = "/signup/{provider}")
//    public CommonResult signupProvider(@ApiParam(value = "서비스 제공자 provider", required = true, defaultValue = "kakao") @PathVariable String provider,
//                                       @ApiParam(value = "소셜 access_token", required = true) @RequestParam String accessToken,
//                                       @ApiParam(value = "이름", required = true) @RequestParam String name,
//                                       @ApiParam(value = "직업") @RequestParam Job job,
//                                       @ApiParam(value = "관심 직군") @RequestParam Interests interests) {
//
//        KakaoProfile profile = kakaoService.getKakaoProfile(accessToken);
//        userDetailService.save(
//                User.builder()
//                        .name(name)
//                        .job(job)
//                        .interests(interests)
//                        .build()
//        );
//        return responseService.getSuccessResult();
//    }
//
//    @ApiOperation(value = "소셜 로그인", notes = "소셜 회원 로그인을 한다.")
//    @PostMapping(value = "/login/{provider}")
//    public SingleResult<String> signinByProvider(
//            @ApiParam(value = "서비스 제공자 provider", required = true, defaultValue = "kakao") @PathVariable String provider,
//            @ApiParam(value = "소셜 access_token", required = true) @RequestParam String accessToken) {
//
//        KakaoProfile profile = kakaoService.getKakaoProfile(accessToken);
//        User user = userRepository.findByIdAndProvider(profile.getId(), provider).orElseThrow(() -> new CDuplicateUserException(profile.getId().toString()));
//        return responseService.getSingleResult(jwtTokenProvider.createToken(user.getId(), user.getEmail(), user.getRole()));
//    }




}
