package com.bevelop.devbevelop.global.config.security.jwt;

import com.bevelop.devbevelop.global.error.ErrorCode;
import com.bevelop.devbevelop.global.error.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Override
    protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // 토큰을 재발급하는 API 경우 토큰 체크 로직 건너뛰기
            String path = request.getServletPath();

            if(path.startsWith("/auth/regenerateToken")) {
                filterChain.doFilter(request, response);
            } else {
                String token = jwtTokenProvider.resolveToken(request);
                if (token != null && jwtTokenProvider.validateAccessToken(token, request)) {
                    this.setAuthentication(token);
                }
                filterChain.doFilter(request, response);
            }

        } catch (ExpiredJwtException e) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .message(ErrorCode.JWT_ACCESS_TOKEN_EXPIRED.getMessage())
                            .code(ErrorCode.JWT_ACCESS_TOKEN_EXPIRED.getCode())
                                    .status(ErrorCode.JWT_ACCESS_TOKEN_EXPIRED.getHttpStatus().value())
                                            .build();
            response.setStatus(ErrorCode.JWT_ACCESS_TOKEN_EXPIRED.getHttpStatus().value());
            response.getWriter().write(convertObjectToJson(errorResponse));
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().flush();
        }
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.writeValueAsString(object);
    }



    // SecurityContext에 Authentication 저장
    private void setAuthentication(String token) {
        Authentication auth = jwtTokenProvider.getAuthenticationByAccessToken(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

}