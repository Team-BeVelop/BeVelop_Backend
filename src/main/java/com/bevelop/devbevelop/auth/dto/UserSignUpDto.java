package com.bevelop.devbevelop.auth.dto;

import com.bevelop.devbevelop.domain.user.domain.Interests;
import com.bevelop.devbevelop.domain.user.domain.Job;
import com.bevelop.devbevelop.domain.user.domain.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString
public class UserSignUpDto {

    @NotEmpty(message = "Please enter your Email")
    @Email
    private String email;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    private String password;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    private Job job;

    private Interests interests;


    /**
     * Transform to User Entity
     * @return User Entity
     */
    public User toUserEntity() {
        return User.builder()
                .email(this.getEmail())
                .password(this.getPassword())
                .name(this.getName())
                .job(this.getJob())
                .interests(this.getInterests())
                .build();
    }
}
