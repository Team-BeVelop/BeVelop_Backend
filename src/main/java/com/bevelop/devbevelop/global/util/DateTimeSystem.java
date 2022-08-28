package com.bevelop.devbevelop.global.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DateTimeSystem {
    public LocalDateTime now() { return LocalDateTime.now();}
}
