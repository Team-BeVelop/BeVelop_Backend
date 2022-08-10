package com.bevelop.devbevelop.domain.auth.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserLogOutDto {

    @NotEmpty(message = "잘못된 요청입니다.")
    private String access_token;

    @NotEmpty(message = "잘못된 요청입니다.")
    private String refresh_token;
}
