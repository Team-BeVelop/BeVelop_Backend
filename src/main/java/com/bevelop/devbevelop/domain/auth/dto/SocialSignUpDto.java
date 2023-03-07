package com.bevelop.devbevelop.domain.auth.dto;

import com.bevelop.devbevelop.domain.user.domain.User;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SocialSignUpDto {

    //이름
    @ApiParam(value = "이름", required = true)
    private String name;

}
