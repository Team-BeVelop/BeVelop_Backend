package com.bevelop.devbevelop.domain.auth.service;

import static org.junit.jupiter.api.Assertions.*;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Random;

import com.bevelop.devbevelop.domain.auth.dto.UserLogInDto;
import com.bevelop.devbevelop.domain.auth.dto.UserSignUpDto;
import com.bevelop.devbevelop.domain.user.domain.User;
import com.bevelop.devbevelop.domain.user.repository.UserRepository;
import com.bevelop.devbevelop.global.common.response.CommonResult;
import com.bevelop.devbevelop.global.common.response.ResponseService;
import com.bevelop.devbevelop.global.common.response.ResponseService.CommonResponse;
import com.bevelop.devbevelop.global.config.security.jwt.JwtTokenProvider;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

//	@Autowired
//	AuthService authService;
//	
//	@Autowired
//	UserRepository userRepository;
//	
//	@Autowired
//	ResponseService responseService;

	@InjectMocks
	AuthServiceImpl authServiceImpl;

	@Spy
	UserRepository userRepository;

	@Spy
	PasswordEncoder passwordEncoder = new MockPasswordEncoder();
	@Spy
	ResponseService responseService;
	@Spy
	JwtTokenProvider jwtTokenProvider;
	@Spy
	AuthenticationManager authenticationManager;
	@Spy
	RedisTemplate<String, String> redisTemplate;

//	@Mock
//	ResponseService responseService;

	private class MockPasswordEncoder implements PasswordEncoder {
		@Override
		public String encode(CharSequence rawPassword) {
			return new StringBuilder(rawPassword).reverse().toString();
		}

		@Override
		public boolean matches(CharSequence rawPassword, String encodedPassword) {
			return encode(rawPassword).equals(encodedPassword);
		}
	}

	@Test
	void testJoinUserSignUpDto() {
		UserSignUpDto userSignUpDto = userSignUpDto();
		when(userRepository.save(any(User.class))).then(AdditionalAnswers.returnsFirstArg());

		CommonResult result = authServiceImpl.join(userSignUpDto);

		assertEquals(result, successResult());
	}

	private UserSignUpDto userSignUpDto() {
		return UserSignUpDto.builder().email("JUnitTest@test.com").nickname("JUnitTest").password("JUnitTest").build();
	}

	private CommonResult successResult() {
		return CommonResult.builder().success(true).code(CommonResponse.SUCCESS.getCode())
				.msg(CommonResponse.SUCCESS.getMsg()).build();
	}

	@Test
	void testJoinUser() {
		fail("Not yet implemented");
	}

	@Test
	void testLoginUserLogInDto() {
		when(authenticationManager.authenticate(any(Authentication.class)))
				.thenReturn(new UsernamePasswordAuthenticationToken(existingUserLoginDto().getEmail(),
						existingUserLoginDto().getPassword()));
//		when(jwtTokenProvider.generateRefreshToken(any(Authentication.class))).thenReturn(value);

		ReflectionTestUtils.setField(jwtTokenProvider, "access_token_secret_key", "bevelop");
		ReflectionTestUtils.setField(jwtTokenProvider, "refresh_token_secret_key", "backendBevelop");
		ReflectionTestUtils.setField(jwtTokenProvider, "access_token_expire_time", 60000);
		ReflectionTestUtils.setField(jwtTokenProvider, "refresh_token_expire_time", 30000);

		ResponseEntity<JSONObject> temp = authServiceImpl.login(existingUserLoginDto());

		assertNotNull(temp);
	}

	private UserLogInDto existingUserLoginDto() {
		return UserLogInDto.builder().email("test@test.com").password("test").build();
	}

	@Test
	void testLoginUser() {
		fail("Not yet implemented");
	}

	@Test
	void testValidateDuplicateEmail() {
		CommonResult answer = authServiceImpl.validateDuplicateEmail("success@success.com");

		assertEquals(successResult(), answer);
	}

	@Test
	void testValidateDuplicateNickname() {
		CommonResult answer = authServiceImpl.validateDuplicateNickname("success");

		assertEquals(successResult(), answer);
	}

}
