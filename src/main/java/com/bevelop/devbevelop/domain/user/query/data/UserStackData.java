package com.bevelop.devbevelop.domain.user.query.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class UserStackData {
    private final Long id;
    private final String stackName;
}
