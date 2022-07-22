package com.bevelop.devbevelop.auth.controller;


import com.bevelop.devbevelop.auth.dto.UserSignUpDto;
import com.bevelop.devbevelop.common.response.CommonResult;
import com.bevelop.devbevelop.config.security.jwt.dto.RegenerateTokenDto;
import com.bevelop.devbevelop.config.security.jwt.dto.TokenDto;
import com.bevelop.devbevelop.auth.dto.UserLoginDto;
import com.bevelop.devbevelop.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public CommonResult signUp(@Validated UserSignUpDto userSignUpDto) { return authService.join(userSignUpDto);}

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@Validated UserLoginDto userLoginDto) { return authService.login(userLoginDto); }

    @PostMapping("/regenerateToken")
    public ResponseEntity<TokenDto> regenerateToken(@Validated RegenerateTokenDto refreshTokenDto) {
        return authService.regenerateToken(refreshTokenDto);
    }


}
