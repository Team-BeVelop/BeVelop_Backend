package com.bevelop.devbevelop.domain.auth.controller;


import com.bevelop.devbevelop.domain.auth.dto.*;
import com.bevelop.devbevelop.domain.auth.service.GithubService;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final GithubService githubService;
    private final UserRepository userRepository;

    @ApiOperation(value = "가입", notes = "회원 가입")
    @PostMapping("/signup")
    public CommonResult signUp(@Validated UserSignUpDto userSignUpDto) { return authService.join(userSignUpDto);}

    @ApiOperation(value = "로그인", notes = "회원 로그인")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Validated UserLogInDto userLoginDto) { return authService.login(userLoginDto); }

    @ApiOperation(value = "토큰 재발급", notes = "회원 토큰 재발급")
    @PostMapping("/regenerateToken")
    public ResponseEntity<TokenDto> regenerateToken(@Validated RegenerateTokenDto refreshTokenDto) {
        return authService.regenerateToken(refreshTokenDto);
    }

    @ApiOperation(value="로그아웃", notes= "회원 로그아웃")
    @PostMapping("/logout")
    public CommonResult logOut(@Validated UserLogOutDto userLogOutDto) { return authService.logout(userLogOutDto); }

    @ApiOperation(value="회원탈퇴", notes= "회원 탈퇴")
    @PostMapping("/remove")
    public CommonResult remove(@AuthenticationPrincipal UserDetails userDetails, @Validated UserWithdrawalDto withdrawalDto ) { return authService.remove(userDetails, withdrawalDto);}

    @ApiOperation(value = "소셜 카카오 계정 가입", notes = "소셜 계정(카카오)을(를) 이용하여 회원가입을 한다.")
    @PostMapping(value = "/signup/kakao")
    public CommonResult signupKakao(@ApiParam(value = "소셜 authentication code", required = true) @RequestParam String code,
                                 @Valid SocialSignUpDto socialSignUpDto) {
        KakaoProfile kakaoProfile = kakaoService.execKakaoLogin(code);
        User user = User.builder()
                .name(socialSignUpDto.getName())
                .provider("kakao")
                .socialId("k"+kakaoProfile.getId())
                .job(socialSignUpDto.getJob())
                .interests(socialSignUpDto.getInterests())
                .build();

        return authService.join(user);
//        return responseService.getSuccessResult();
//        return "redirect:webauthcallback://success?customToken="+jwtTokenProvider.createToken(user.getId(), user.getRole());
    }

    @ApiOperation(value = "소셜 깃허브 계정 가입", notes = "소셜 계정(깃허브)을(를) 이용하여 회원가입을 한다.")
    @PostMapping(value = "/signup/github")
    public CommonResult signupGithub(@ApiParam(value = "소셜 authentication code", required = true) @RequestParam String code,
                                       @Valid SocialSignUpDto socialSignUpDto) {
        GithubProfile githubProfile = githubService.execGithubLogin(code);
        User user = User.builder()
                .name(socialSignUpDto.getName())
                .provider("github")
                .socialId("g"+githubProfile.getId())
                .job(socialSignUpDto.getJob())
                .interests(socialSignUpDto.getInterests())
                .build();
        return authService.join(user);
//        return responseService.getSuccessResult();
//        return "redirect:webauthcallback://success?customToken="+jwtTokenProvider.createToken(user.getId(), user.getRole());
    }

    @ApiOperation(value = "소셜 카카오 로그인", notes = "소셜 회원(카카오)을(를) 로그인을 한다.")
    @PostMapping(value = "/login/kakao")
    public ResponseEntity<TokenDto> loginByKakao(
            @ApiParam(value = "소셜 authentication code", required = true) @RequestParam String code) {

        KakaoProfile profile = kakaoService.execKakaoLogin(code);
        User user = userRepository.findBySocialIdAndProvider("k"+profile.getId(), "kakao").orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        return authService.login(user);
//        return responseService.getSingleResult(jwtTokenProvider.createToken(user.getId(), user.getRole()));
//        return "redirect:webauthcallback://success?customToken="+jwtTokenProvider.createToken(user.ge tId(), user.getRole());
    }

    @ApiOperation(value = "소셜 깃허브 로그인", notes = "소셜 회원(깃허브)을(를) 로그인을 한다.")
    @PostMapping(value = "/login/github")
    public ResponseEntity<TokenDto> loginByGithub(
            @ApiParam(value = "소셜 authentication code", required = true) @RequestParam String code) {

        GithubProfile profile = githubService.execGithubLogin(code);
        User user = userRepository.findBySocialIdAndProvider("g"+profile.getId(), "github").orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        return authService.login(user);
//        return responseService.getSingleResult(jwtTokenProvider.createToken(user.getId(), user.getRole()));
//        return "redirect:webauthcallback://success?customToken="+jwtTokenProvider.createToken(user.ge tId(), user.getRole());
    }

}
