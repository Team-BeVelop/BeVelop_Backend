package com.bevelop.devbevelop.domain.auth.service;

import com.bevelop.devbevelop.domain.auth.dto.GithubProfile;
import com.bevelop.devbevelop.domain.auth.dto.RetSocialAuth;
import com.bevelop.devbevelop.global.error.ErrorCode;
import com.bevelop.devbevelop.global.error.exception.CustomException;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GithubService {

    private final RestTemplate restTemplate;
    private final Environment env;
    private final Gson gson;

    @Value("${spring.url.base}")
    private String baseUrl;

    @Value("${spring.social.github.client_id}")
    private String githubClientId;

    @Value("${spring.social.github.redirect}")
    private String githubRedirect;

    @Value("${spring.social.github.client_secret}")
    private String githubClientSecret;

    public GithubProfile execGithubLogin(String code) {
        String accesesToken = getGithubTokenInfo(code).getAccess_token();
        GithubProfile githubProfile = getGithubProfile(accesesToken);
        return githubProfile;
    }

    public GithubProfile getGithubProfile(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(new MediaType[] {MediaType.APPLICATION_JSON}));
        headers.set("Authorization", "token " + accessToken);

        // Set http entity
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);
        try {
            // Request profile
            ResponseEntity<String> response = restTemplate.exchange(env.getProperty("spring.social.github.url.profile"), HttpMethod.GET, request, String.class);
            if (response.getStatusCode() == HttpStatus.OK)
                return gson.fromJson(response.getBody(), GithubProfile.class);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.COMMUNICATION_ERROR);
        }
        throw new CustomException(ErrorCode.COMMUNICATION_ERROR);
    }

    public RetSocialAuth getGithubTokenInfo(String code) {
        // Set header : Content-type: application/x-www-form-urlencoded
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
        // Set parameter
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", githubClientId);
        params.add("client_secret", githubClientSecret);
        params.add("code", code);
        // Set http entity
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(env.getProperty("spring.social.github.url.token"), request, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return gson.fromJson(response.getBody(), RetSocialAuth.class);
        }
        return null;
    }
}