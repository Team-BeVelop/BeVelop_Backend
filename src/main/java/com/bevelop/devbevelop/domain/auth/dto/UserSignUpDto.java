package com.bevelop.devbevelop.domain.auth.dto;

import com.bevelop.devbevelop.domain.study.domain.AttachedStack;
import com.bevelop.devbevelop.domain.study.domain.AttachedStacks;
import com.bevelop.devbevelop.domain.user.domain.Interests;
import com.bevelop.devbevelop.domain.user.domain.Job;
import com.bevelop.devbevelop.domain.user.domain.User;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class UserSignUpDto {
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

    @ApiParam(value = "직업")
    private Job job;

    @ApiParam(value = "관심 직군")
    private Interests interests;

    @ApiParam(value = "기술 스택")
    private Set<String> stackName;


    public Set<String> getStackName() {
        return stackName == null ? Set.of() : stackName;
    }

    public AttachedStacks mapToAttachedStacks() {
        final Set<AttachedStack> attachedStacks = getStackName().stream()
                .map(AttachedStack::new)
                .collect(Collectors.toSet());
        return new AttachedStacks(attachedStacks);
    }


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
                .attachedStacks(mapToAttachedStacks())
                .build();
    }
}
