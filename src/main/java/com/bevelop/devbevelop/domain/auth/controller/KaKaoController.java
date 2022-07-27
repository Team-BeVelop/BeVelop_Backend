//package com.bevelop.devbevelop.domain.auth.controller;
//
//import com.bevelop.devbevelop.domain.auth.service.KakaoService;
//import com.google.gson.Gson;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.servlet.ModelAndView;
//
//@Api(value = "카카오")
//@Controller
//@RequestMapping("/social/login")
//public class KaKaoController {
//
//    private Environment env;
//    private RestTemplate restTemplate;
//    private Gson gson;
//    private KakaoService kakaoService;
//
//    @Value("${spring.url.base}")
//    private String baseUrl;
//
//    @Value("${spring.social.kakao.client_id}")
//    private String kakaoClientId;
//
//    @Value("${spring.social.kakao.redirect}")
//    private String kakaoRedirect;
//
//    @ApiOperation(value = "카카오 로그인 페이지")
//    @GetMapping
//    public ModelAndView socialLogin(ModelAndView mav) {
//
//        StringBuilder loginUrl = new StringBuilder()
//                .append(env.getProperty("spring.social.kakao.url.login"))
//                .append("?client_id=").append(kakaoClientId)
//                .append("&response_type=code")
//                .append("&redirect_uri=").append(baseUrl).append(kakaoRedirect);
//
//        mav.addObject("loginUrl", loginUrl);
//        mav.setViewName("social/login");
//        return mav;
//    }
//
//    @ApiOperation(value = "카카오 인증 완료 후 리다이렉트 화면")
//    @GetMapping(value = "/kakao")
//    public ModelAndView redirectKakao(ModelAndView mav, @RequestParam String code) {
//        mav.addObject("authInfo", kakaoService.getKakaoTokenInfo(code));
//        mav.setViewName("social/redirectKakao");
//        return mav;
//    }
//}