package com.bevelop.devbevelop.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GithubProfile {
    @JsonProperty
    private Long id;

    @JsonProperty
    private String email;

    @JsonProperty
    private String name;
}
