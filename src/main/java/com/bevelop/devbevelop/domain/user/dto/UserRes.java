package com.bevelop.devbevelop.domain.user.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UserRes {

    @NotNull
    private final String name;

    @NotNull
    private final String email;
}
