package com.bevelop.devbevelop.domain.auth.dto;

import com.bevelop.devbevelop.domain.user.domain.Interests;
import com.bevelop.devbevelop.domain.user.domain.Job;
import com.bevelop.devbevelop.domain.user.domain.Role;
import com.bevelop.devbevelop.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@NoArgsConstructor
@Builder
public class UserDtos {

    @Getter
    public static class UserLogInRes {

        private Long id;
        private String email;
        private String name;
        private Role role;
        private Interests interests;
        private Job job;


        public UserLogInRes(User user) {
            this.id = user.getId();
            this.email = user.getEmail();
            this.name = user.getName();
            this.role = user.getRole();
            this.interests = user.getInterests();
            this.job = user.getJob();
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