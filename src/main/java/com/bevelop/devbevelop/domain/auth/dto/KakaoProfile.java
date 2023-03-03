package com.bevelop.devbevelop.domain.auth.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoProfile {
    private Long id;
    private Properties properties;
    private KakaoAccount kakao_account;

    @Getter
    public static class Properties {
        private String nickname;
    }
    
    @Getter
	public static class KakaoAccount {
    	private String email;
    }
}