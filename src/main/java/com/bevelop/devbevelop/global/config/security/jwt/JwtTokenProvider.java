package com.bevelop.devbevelop.global.config.security.jwt;


import com.bevelop.devbevelop.global.error.ErrorCode;
import com.bevelop.devbevelop.global.error.exception.BaseException;
import com.bevelop.devbevelop.global.error.exception.CustomException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${jwt.token.access-token-secret-key}")
    private String access_token_secret_key;

    @Value("${jwt.token.refresh-token-secret-key}")
    private String refresh_token_secret_key;

    @Value("${jwt.token.access-token-expire-length}")
    private long access_token_expire_time;

    @Value("${jwt.token.refresh-token-expire-length}")
    private long refresh_token_expire_time;


    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 적절한 설정을 통해 Access 토큰을 생성하여 반환
     * @param authentication
     * @return access token
     */

    public String generateAccessToken(Authentication authentication) {
        Claims claims = Jwts.claims().setSubject(authentication.getName());

        Date now = new Date();
        Date expiresIn = new Date(now.getTime() + access_token_expire_time);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiresIn)
                .signWith(SignatureAlgorithm.HS256, access_token_secret_key)
                .compact();
    }

    /**
     * 적절한 설정을 통해 Refresh 토큰을 생성하여 반환
     * @param authentication
     * @return refresh token
     */
    public String generateRefreshToken(Authentication authentication) {
        Claims claims = Jwts.claims().setSubject(authentication.getName());

        Date now = new Date();
        Date expiresIn = new Date(now.getTime() + refresh_token_expire_time);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiresIn)
                .signWith(SignatureAlgorithm.HS256, refresh_token_secret_key)
                .compact();
    }

    /**
     * Access 토큰으로부터 클레임을 만들고, 이를 통해 User 객체를 생성하여 Authentication 객체를 반환
     * @param access_token
     * @return
     */
    public Authentication getAuthenticationByAccessToken(String access_token) {
        String userPrincipal = Jwts.parser().setSigningKey(access_token_secret_key).parseClaimsJws(access_token).getBody().getSubject();
        UserDetails userDetails = userDetailsService.loadUserByUsername(userPrincipal);

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * Refresh 토큰으로부터 클레임을 만들고, 이를 통해 User 객체를 생성하여 Authentication 객체를 반환
     * @param refresh_token
     * @return
     */
    public Authentication getAuthenticationByRefreshToken(String refresh_token) {
        String userPrincipal = Jwts.parser().setSigningKey(refresh_token_secret_key).parseClaimsJws(refresh_token).getBody().getSubject();
        UserDetails userDetails = userDetailsService.loadUserByUsername(userPrincipal);

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * http 헤더로부터 bearer 토큰을 가져옴.
     * @param req
     * @return
     */
    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }


    /**
     * Access 토큰을 검증
     * @param token
     * @return
     */
    public boolean validateAccessToken(String token) {
        try {
            Jwts.parser().setSigningKey(access_token_secret_key).parseClaimsJws(token);
            return true;
        } catch(SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature");
            return false;
        } catch(UnsupportedJwtException e) {
            log.error("Unsupported JWT token");
            return false;
        } catch(IllegalArgumentException e) {
            log.error("JWT token is invalid");
            return false;
        }
    }

    /**
     * Refresh 토큰을 검증
     * @param token
     * @return
     */
    public boolean validateRefreshToken(String token) {
        try {
            Jwts.parser().setSigningKey(refresh_token_secret_key).parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            // MalformedJwtException | ExpiredJwtException | IllegalArgumentException
            throw new BaseException("Error on Refresh Token", HttpStatus.INTERNAL_SERVER_ERROR);
//            throw new CustomException(ErrorCode.INVALID_AUTH_REFRESH_TOKEN);
        }
    }

    public Long getExpiration(String token) {
        Date expiration = Jwts.parser().setSigningKey(access_token_secret_key).parseClaimsJws(token).getBody().getExpiration();
        Long now = new Date().getTime();
        return (expiration.getTime() - now);
    }


}