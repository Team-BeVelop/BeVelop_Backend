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
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import javax.validation.Valid;

@Api(tags = { "1. Auth Controller" })
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;
	private final KakaoService kakaoService;
	private final GithubService githubService;
//	private final UserRepository userRepository;

	@ApiOperation(value = "회원가입", notes = "회원 가입")
	@PostMapping("/signup")
	public CommonResult signUp(@Valid @RequestBody UserSignUpDto userSignUpDto) {
		return authService.join(userSignUpDto);
	}
	
	@ApiOperation(value = "닉네임 중복 체크")
	@PostMapping("/validate/nickname")
	public CommonResult validateNickname(String nickname) {
		return authService.validateDuplicateNickname(nickname);
	}

	@ApiOperation(value = "로그인", notes = "회원 로그인")
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody UserLogInDto userLoginDto) {
		return authService.login(userLoginDto);
	}

    @ApiOperation(value = "토큰 재발급", notes = "회원 토큰 재발급")
    @PostMapping("/regenerateToken")
    public ResponseEntity<TokenDto> regenerateToken(@Valid @RequestBody RegenerateTokenDto refreshTokenDto) {
        return authService.regenerateToken(refreshTokenDto);
    }

//	@ApiOperation(value = "토큰 재발급", notes = "회원 토큰 재발급")
//	@PostMapping("/regenerateToken")
//	public ResponseEntity<TokenDto> regenerateToken(@Valid @RequestBody String refreshToken) {
//		return authService.regenerateToken(refreshToken);
//	}

	@ApiOperation(value = "로그아웃", notes = "회원 로그아웃")
	@PostMapping("/logout")
	public CommonResult logOut(@Valid @RequestBody UserLogOutDto userLogOutDto) {
		return authService.logout(userLogOutDto);
	}

	@ApiOperation(value = "회원탈퇴", notes = "회원 탈퇴")
	@PostMapping("/remove")
	public CommonResult remove(@AuthenticationPrincipal UserDetails userDetails,
			@Valid @RequestBody UserWithdrawalDto withdrawalDto) {
		return authService.remove(userDetails, withdrawalDto);
	}

	@ApiOperation(value = "소셜 카카오 계정 가입", notes = "소셜 계정(카카오)을(를) 이용하여 회원가입을 한다.")
	@PostMapping(value = "/signup/kakao")
	public CommonResult signupKakao(
			@ApiParam(value = "소셜 authentication code", required = true) @RequestParam String code) {
		
		String kakaoToken = kakaoService.getKakaoTokenInfo(code).getAccess_token();
		KakaoProfile result = kakaoService.getUserInfo(kakaoToken);
        String nickname = result.getProperties().getNickname();
        String email = result.getKakao_account().getEmail();
        String password = String.valueOf(result.getId());

		return authService.join(UserSignUpDto.builder().nickname(nickname).email(email).password(password).build());
	}

	@ApiOperation(value = "소셜 깃허브 계정 가입", notes = "소셜 계정(깃허브)을(를) 이용하여 회원가입을 한다.")
	@PostMapping(value = "/signup/github")
	public CommonResult signupGithub(
			@ApiParam(value = "소셜 authentication code", required = true) @RequestParam String code) {
		
		String githubToken = githubService.getGithubTokenInfo(code).getAccess_token();
		GithubProfile result = githubService.getUserInfo(githubToken);
        String nickname = (String) result.getName();
        String email = (String) result.getEmail();
        String password = String.valueOf(result.getId());

		return authService.join(UserSignUpDto.builder().nickname(nickname).email(email).password(password).build());
	}

	@ApiOperation(value = "소셜 카카오 로그인", notes = "소셜 회원(카카오)을(를) 로그인을 한다.")
	@PostMapping(value = "/login/kakao")
	public ResponseEntity<?> loginByKakao(
			@ApiParam(value = "소셜 authentication code", required = true) @RequestParam String code) {

		String kakaoToken = kakaoService.getKakaoTokenInfo(code).getAccess_token();
		KakaoProfile result = kakaoService.getUserInfo(kakaoToken);
        String nickname = result.getProperties().getNickname();
        String email = result.getKakao_account().getEmail();
        String password = String.valueOf(result.getId());

		return authService.login(UserLogInDto.builder().email(email).password(password).build());
	}

	@ApiOperation(value = "소셜 깃허브 로그인", notes = "소셜 회원(깃허브)을(를) 로그인을 한다.")
	@PostMapping(value = "/login/github")
	public ResponseEntity<?> loginByGithub(
			@ApiParam(value = "소셜 authentication code", required = true) @RequestParam String code) {

		String githubToken = githubService.getGithubTokenInfo(code).getAccess_token();
		GithubProfile result = githubService.getUserInfo(githubToken);
        String nickname = (String) result.getName();
        String email = (String) result.getEmail();
        String password = String.valueOf(result.getId());
		return authService.login(UserLogInDto.builder().email(email).password(password).build());
	}

}
