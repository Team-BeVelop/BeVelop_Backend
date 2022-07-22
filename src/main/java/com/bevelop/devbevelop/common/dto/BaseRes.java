package com.bevelop.devbevelop.common.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaseRes {
    private boolean ok;
    private String error;
}
