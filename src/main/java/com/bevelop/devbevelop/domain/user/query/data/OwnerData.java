package com.bevelop.devbevelop.domain.user.query.data;

import lombok.*;
import org.codehaus.jackson.annotate.JsonProperty;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class OwnerData {

    @JsonProperty
    private Long userId;

    private String email;

    private String name;
}
