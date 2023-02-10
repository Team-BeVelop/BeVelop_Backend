package com.bevelop.devbevelop.domain.auth.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bevelop.devbevelop.domain.auth.dto.UserSignUpDto;
import com.bevelop.devbevelop.domain.auth.service.AuthService;
import com.bevelop.devbevelop.global.common.response.CommonResult;
import com.bevelop.devbevelop.global.common.response.ResponseService;
import com.bevelop.devbevelop.global.common.response.ResponseService.CommonResponse;

@ExtendWith(MockitoExtension.class)
//@SpringBootTest
class AuthControllerTest {

	@Mock
	AuthService authService;

	@Spy
	@InjectMocks
	AuthController authController;
	
//	@Autowired
//	ResponseService responseService;
	
	@Before
	public void setup() {
	    MockitoAnnotations.openMocks(this);
//	    this.authController=new AuthController(authService);
	}

	@Test
	void testSignUp() {
		UserSignUpDto userSignUpDto = new UserSignUpDto();
		userSignUpDto.setEmail("JUnitTest@test.com");
		userSignUpDto.setNickname("JUnitTest");
		userSignUpDto.setPassword("JUnitTest");
		CommonResult result = authController.signUp(userSignUpDto);

		CommonResult successResult = new CommonResult();
		successResult.setSuccess(true);
		successResult.setCode(CommonResponse.SUCCESS.getCode());
		successResult.setMsg(CommonResponse.SUCCESS.getMsg());
		
		assertEquals(successResult, result);
	}

	@Test
	void testValidateNickname() {
		fail("Not yet implemented");
	}

	@Test
	void testLogin() {
		fail("Not yet implemented");
	}

}
