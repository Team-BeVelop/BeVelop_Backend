package com.bevelop.devbevelop.domain.auth.dto;

import com.bevelop.devbevelop.domain.user.domain.Role;
import com.bevelop.devbevelop.domain.user.domain.User;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpDto {
    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    @Email
    @ApiParam(value = "이메일", required = true)
    private String email;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
//    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,10}$",
//            message = "비밀번호는 하나 이상의 대문자, 소문자, 숫자 및 특수 문자로 이루어진 6 ~ 10자이여야 합니다.")
    @ApiParam(value = "비밀번호", required = true)
    private String password;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @ApiParam(value = "닉네임", required = true)
    private String nickname;

    /**
     * Transform to User Entity
     * @return User Entity
     */
    public User toUserEntity() {
        return User.builder()
                .email(this.getEmail())
                .password(this.getPassword())
                .nickname(this.getNickname())
                .role(Role.SLAVE)
                .build();
    }
}
