package com.bevelop.devbevelop.domain.auth.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bevelop.devbevelop.domain.auth.dto.UserSignUpDto;
import com.bevelop.devbevelop.domain.user.repository.UserRepository;
import com.bevelop.devbevelop.global.common.response.CommonResult;
import com.bevelop.devbevelop.global.common.response.ResponseService;

@SpringBootTest
class AuthServiceImplTest {
	
//	@Autowired
//	AuthService authService;
//	
//	@Autowired
//	UserRepository userRepository;
//	
//	@Autowired
//	ResponseService responseService;

	@Spy
	@InjectMocks
	AuthServiceImpl authServiceImpl;
	
	@Mock
	UserRepository userRepository;
	
	@Mock
	ResponseService responseService;
	
	@BeforeEach
	public void setup() {
	    MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testJoinUserSignUpDto() {
		UserSignUpDto userSignUpDto = new UserSignUpDto();
		userSignUpDto.setEmail("JUnitTest2@test.com");
		userSignUpDto.setNickname("JUnitTest2");
		userSignUpDto.setPassword("JUnitTest2");
		
		CommonResult result = authServiceImpl.join(userSignUpDto);
			
		assertEquals(result, responseService.getSuccessResult());
	}

	@Test
	void testJoinUser() {
		fail("Not yet implemented");
	}

	@Test
	void testLoginUserLogInDto() {
		fail("Not yet implemented");
	}

	@Test
	void testLoginUser() {
		fail("Not yet implemented");
	}

	@Test
	void testValidateDuplicateEmail() {
		fail("Not yet implemented");
	}

	@Test
	void testValidateDuplicateNickname() {
		fail("Not yet implemented");
	}

}
