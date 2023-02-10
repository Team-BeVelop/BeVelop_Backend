package com.bevelop.devbevelop.domain.auth.dto;

import com.bevelop.devbevelop.domain.user.domain.Role;
import com.bevelop.devbevelop.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
@Builder
public class UserDtos {

    @Getter
    public static class UserLogInRes {

        private Long id;
        private String email;
        private String nickname;
        private Role role;
        private String interests;
        private String job;

        private Set<?> stackName;




        public UserLogInRes(User user) {
            this.id = user.getId();
            this.email = user.getEmail();
            this.nickname = user.getNickname();
            this.role = user.getRole();
            this.interests = user.getInterests();
            this.job = user.getJob();
            this.stackName = user.getAttachedStacks().getValue();
        }
    }

    @Getter
    public static class TokensDto {
        private String access_token;
        private String refresh_token;

        public TokensDto(String access_token, String refresh_token) {
            this.access_token = access_token;
            this.refresh_token = refresh_token;
        }
    }

}