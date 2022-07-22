package com.bevelop.devbevelop.domain.user.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UserReq {
    @NotNull
    private final String email;

    @NotNull
    private final String password;

    @NotNull
    private final String name;

}
