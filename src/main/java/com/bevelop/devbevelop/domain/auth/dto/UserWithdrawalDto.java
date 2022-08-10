package com.bevelop.devbevelop.domain.auth.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class UserWithdrawalDto {
    @NotBlank(message = "비밀번호를 입력해주세요")
    private String check_password;
}
