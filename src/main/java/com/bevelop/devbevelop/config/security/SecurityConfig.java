package com.bevelop.devbevelop.config.security;


import com.bevelop.devbevelop.config.security.jwt.CustomAccessDeniedHandler;
import com.bevelop.devbevelop.config.security.jwt.CustomAuthenticationEntryPoint;
import com.bevelop.devbevelop.config.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Disable csrf to use token
        http
                .csrf().disable();

        //
        http
                .authorizeRequests()
                .antMatchers(
                        "/",
                        "/auth/signup",
                        "/user/userList",
                        "/auth/login*",
                        "/user/profile/view/**",
                        "/auth/regenerateToken",
                        "/favicon.ico"
                ).permitAll()
                .anyRequest().authenticated();

        // No session will be created or used by spring security
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // exception handling for jwt
        http
                .exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler)
                .authenticationEntryPoint(customAuthenticationEntryPoint);

        // Apply JWT
        http.apply(new JwtSecurityConfig(jwtTokenProvider));

        return http.build();
    }



}
