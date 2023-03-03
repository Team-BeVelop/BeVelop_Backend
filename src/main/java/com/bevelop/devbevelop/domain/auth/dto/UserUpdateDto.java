package com.bevelop.devbevelop.domain.auth.dto;

import com.bevelop.devbevelop.domain.user.domain.AttachedStack;
import com.bevelop.devbevelop.domain.user.domain.AttachedStacks;
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

    @ApiParam(value = "이름")
    private String nickname;

    @ApiParam(value = "한줄소개")
    private String introduce;

    @ApiParam(value = "직업")
    private String job;

    @ApiParam(value = "관심 직군")
    private String interests;

    @ApiParam(value = "링크")
    private String url;

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
                .nickname(this.getNickname())
                .introduce(this.getIntroduce())
                .job(this.getJob())
                .interests(this.getInterests())
                .url(this.getUrl())
                .attachedStacks(mapToAttachedStacks())
                .build();

    }

}
