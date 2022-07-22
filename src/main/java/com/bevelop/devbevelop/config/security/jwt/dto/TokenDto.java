package com.bevelop.devbevelop.config.security.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TokenDto {
    private String access_token;
    private String refresh_token;
}
