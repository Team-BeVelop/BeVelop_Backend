package com.bevelop.devbevelop.domain.study.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ParticipateStudyDto {

    @NotBlank(message = "내용을 입력해 주세요.")
    private String message;
}
