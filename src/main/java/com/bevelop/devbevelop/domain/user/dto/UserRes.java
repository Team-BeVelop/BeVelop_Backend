package com.bevelop.devbevelop.domain.user.dto;

import io.swagger.annotations.ApiParam;
import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UserRes {


    @NotBlank(message = "이름은 필수 입력 값입니다.")
    @ApiParam(value = "이름", required = true)
    private String name;

    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    @Email
    @ApiParam(value = "이메일", required = true)
    private String email;

}
