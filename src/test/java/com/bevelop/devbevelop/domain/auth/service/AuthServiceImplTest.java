package com.bevelop.devbevelop.domain.auth.service;

import static org.junit.jupiter.api.Assertions.*;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.bevelop.devbevelop.domain.auth.dto.UserLogInDto;
import com.bevelop.devbevelop.domain.auth.dto.UserSignUpDto;
import com.bevelop.devbevelop.domain.user.domain.User;
import com.bevelop.devbevelop.domain.user.repository.UserRepository;
import com.bevelop.devbevelop.global.common.response.CommonResult;
import com.bevelop.devbevelop.global.common.response.ResponseService;
import com.bevelop.devbevelop.global.common.response.ResponseService.CommonResponse;
import com.bevelop.devbevelop.global.config.security.jwt.JwtTokenProvider;


@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {
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
	
	RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();

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
		when(userRepository.save(any(User.class))).then(AdditionalAnswers.returnsFirstArg());

		CommonResult result = authServiceImpl.join(userSignUpDto());

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
		when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(existingUserLoginDto().toUserEntity()));
//		when(redisTemplate.opsForValue()).thenReturn(new DefaultValueOperations<>(redisTemplate));
//		Mockito.doNothing().when(redisTemplate.opsForValue()).set(any(String.class), any(String.class), any(long.class), any(TimeUnit.class));
		
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

}
