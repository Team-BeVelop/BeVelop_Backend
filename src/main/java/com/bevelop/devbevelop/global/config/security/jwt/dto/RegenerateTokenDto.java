package com.bevelop.devbevelop.global.config.security.jwt.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor @Builder
public class RegenerateTokenDto {
    private String refresh_token;
}
