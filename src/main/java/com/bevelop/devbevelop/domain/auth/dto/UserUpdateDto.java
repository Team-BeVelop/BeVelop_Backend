package com.bevelop.devbevelop.domain.auth.dto;

import com.bevelop.devbevelop.domain.user.domain.AttachedStack;
import com.bevelop.devbevelop.domain.user.domain.AttachedStacks;
import com.bevelop.devbevelop.domain.user.domain.Interests;
import com.bevelop.devbevelop.domain.user.domain.Job;
import com.bevelop.devbevelop.domain.user.domain.User;
import io.swagger.annotations.ApiParam;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UserUpdateDto {

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

    public User toUserEntity() {
        return User.builder()
                .name(this.getName())
                .job(this.getJob())
                .interests(this.getInterests())
                .attachedStacks(mapToAttachedStacks())
                .build();

    }

}
