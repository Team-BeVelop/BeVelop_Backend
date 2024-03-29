package com.bevelop.devbevelop.domain.auth.dto;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.bevelop.devbevelop.domain.user.domain.Role;
import com.bevelop.devbevelop.domain.user.domain.User;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class UserLogInDto {
    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    @Email
    @ApiParam(value = "이메일", required = true)
    private String email;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    @ApiParam(value = "비밀번호", required = true)
    private String password;
    
    /**
     * Transform to User Entity
     * @return User Entity
     */
    public User toUserEntity() {
        return User.builder()
                .email(this.getEmail())
                .password(this.getPassword())
                .role(Role.SLAVE)
                .build();
    }

	@Override
	public String toString() {
		return "UserLogInDto [email=" + email + ", password=" + password + "]";
	}
    
    
}
