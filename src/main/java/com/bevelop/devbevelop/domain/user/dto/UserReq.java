package com.bevelop.devbevelop.domain.user.dto;

import io.swagger.annotations.ApiParam;
import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UserReq {
    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    @Email
    @ApiParam(value = "이메일", required = true)
    private String email;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,10}$",
            message = "비밀번호는 하나 이상의 대문자, 소문자, 숫자 및 특수 문자로 이루어진 6 ~ 10자이여야 합니다.")
    @ApiParam(value = "비밀번호", required = true)
    private String password;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    @ApiParam(value = "이름", required = true)
    private String name;

}
