//package com.bevelop.devbevelop.global.config.security;
//
//
//import com.bevelop.devbevelop.global.config.security.jwt.CustomAccessDeniedHandler;
//import com.bevelop.devbevelop.global.config.security.jwt.CustomAuthenticationEntryPoint;
//import com.bevelop.devbevelop.global.config.security.jwt.JwtTokenProvider;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.client.RestTemplate;
//
//
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    private final JwtTokenProvider jwtTokenProvider;
//    private final CustomAccessDeniedHandler customAccessDeniedHandler;
//    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//    @Bean
//    public RestTemplate getRestTemplate() {
//        return new RestTemplate();
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        // Disable csrf to use token
//        http
//                .csrf().disable();
//
//        //
//        http
//                .authorizeRequests()
//                .antMatchers(
//                        "/",
//                        "/auth/signup",
//                        "/user/userList",
//                        "/auth/login*",
//                        "/user/profile/view/**",
//                        "/auth/regenerateToken",
//                        "/favicon.ico"
//                ).permitAll()
//                .anyRequest().authenticated();
//
//        // No session will be created or used by spring security
//        http
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        // exception handling for jwt
//        http
//                .exceptionHandling()
//                .accessDeniedHandler(customAccessDeniedHandler)
//                .authenticationEntryPoint(customAuthenticationEntryPoint);
//
//        // Apply JWT
//        http.apply(new JwtSecurityConfig(jwtTokenProvider));
//
//        return http.build();
//    }
//
//
//
//}

package com.bevelop.devbevelop.global.config.security;

import com.bevelop.devbevelop.global.config.security.jwt.JwtAuthenticationFilter;
import com.bevelop.devbevelop.global.config.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration {

    private final JwtTokenProvider jwtTokenProvider;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception    {
        http
                .httpBasic().disable() // rest api ????????? ???????????? ????????????. ??????????????? ???????????? ???????????? ???????????? ??????????????? ??????.
                .csrf().disable() // rest api????????? csrf ????????? ?????????????????? disable??????.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt token?????? ????????????????????? ???????????????????????? ????????????.
                .and()
                .authorizeRequests() // ?????? ??????????????? ?????? ???????????? ??????
                .antMatchers(("/user/hello")).permitAll()
//                .antMatchers("/*/signin", "/*/signup").permitAll() // ?????? ??? ?????? ????????? ????????? ????????????
//                .antMatchers("/*/users").hasRole("MASTER")
//                .anyRequest().hasRole("SLAVE") // ?????? ????????? ????????? ?????? ????????? ????????? ?????? ??????
                .anyRequest().permitAll()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class); // jwt token ????????? id/password ?????? ?????? ?????? ?????????.

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) ->web.ignoring().antMatchers(
                // -- Static resources
                "/css/**", "/images/**", "/js/**"
                // -- Swagger UI v2
                , "/v2/api-docs", "/swagger-resources/**"
                , "/swagger-ui.html", "/webjars/**", "/swagger/**"
                // -- Swagger UI v3 (Open API)
                , "/v3/api-docs/**", "/swagger-ui/**"
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
