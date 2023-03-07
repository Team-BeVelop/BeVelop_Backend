package com.bevelop.devbevelop.domain.auth.service;

import com.bevelop.devbevelop.domain.auth.dto.GithubProfile;
import com.bevelop.devbevelop.domain.auth.dto.RetSocialAuth;
import com.bevelop.devbevelop.global.error.ErrorCode;
import com.bevelop.devbevelop.global.error.exception.CustomException;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    
    public GithubProfile getUserInfo(String access_token) {
    	HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(new MediaType[] {MediaType.APPLICATION_JSON}));
        headers.set("Accept", "application/vnd.github+json");
        headers.set("Authorization", "Bearer " + access_token);
        headers.set("X-GitHub-Api-Version", "2022-11-28");

        // Set http entity
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);
        try {
            // Request profile
            ResponseEntity<String> response = restTemplate.exchange(env.getProperty("spring.social.github.url.profile"), HttpMethod.GET, request, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
            	GithubProfile githubProfile = gson.fromJson(response.getBody(), GithubProfile.class);
            	if(githubProfile.getEmail()==null) {
            		githubProfile.setEmail(githubProfile.getId()+"@github.com");
            	}	
            	return githubProfile;
            }
        } catch (Exception e) {
            throw new CustomException(ErrorCode.COMMUNICATION_ERROR);
        }
        throw new CustomException(ErrorCode.COMMUNICATION_ERROR);
	}

}