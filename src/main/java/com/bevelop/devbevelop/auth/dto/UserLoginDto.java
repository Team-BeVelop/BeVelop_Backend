package com.bevelop.devbevelop.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@ToString
public class UserLoginDto {
    @NotEmpty(message = "Please enter your Email")
    @Email
    private String email;
    @NotEmpty(message = "Please enter your Password")
    private String password;

    @Builder
    public UserLoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
