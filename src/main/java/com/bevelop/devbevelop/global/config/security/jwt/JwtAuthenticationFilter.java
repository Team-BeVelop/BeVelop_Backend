package com.bevelop.devbevelop.global.config.security.jwt;

import com.bevelop.devbevelop.global.common.response.ResponseVO;
import com.bevelop.devbevelop.global.error.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
                if (token != null && jwtTokenProvider.validateAccessToken(token)) {
                    this.setAuthentication(token);
                }
                filterChain.doFilter(request, response);
            }

        } catch (ExpiredJwtException e) {
            ResponseVO responseVO = ResponseVO.builder()
                    .status(ErrorCode.JWT_ACCESS_TOKEN_EXPIRED.getHttpStatus())
                    .message(ErrorCode.JWT_ACCESS_TOKEN_EXPIRED.getMessage())
                    .code(ErrorCode.JWT_ACCESS_TOKEN_EXPIRED.getCode())
                    .build();

            response.setStatus(ErrorCode.JWT_ACCESS_TOKEN_EXPIRED.getHttpStatus().value());
            response.getWriter().write(new ObjectMapper().writeValueAsString(responseVO));
            response.getWriter().flush();
        }
    }

    // SecurityContext에 Authentication 저장
    private void setAuthentication(String token) {
        Authentication auth = jwtTokenProvider.getAuthenticationByAccessToken(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

}