//package com.bevelop.devbevelop.domain.auth.controller;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.doReturn;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.mockito.ArgumentMatchers.any;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import com.bevelop.devbevelop.domain.auth.dto.UserSignUpDto;
//import com.bevelop.devbevelop.domain.auth.service.AuthService;
//import com.bevelop.devbevelop.global.common.response.CommonResult;
//import com.bevelop.devbevelop.global.common.response.ResponseService.CommonResponse;
//import com.google.gson.Gson;
//
//@ExtendWith(MockitoExtension.class)
////@SpringBootTest
//class AuthControllerTest {
//
//	@Mock
//	AuthService authService;
//
//	@InjectMocks
//	AuthController authController;
//
////	@Autowired
////	ResponseService responseService;
//
//	private MockMvc mockMvc;
//
//	@BeforeEach
//	public void setup() {
//
//		mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
//
////		MockitoAnnotations.openMocks(this);
////	    this.authController=new AuthController(authService);
//	}
//
//	@Test
//	void testSignUp() {
////		UserSignUpDto userSignUpDto = new UserSignUpDto();
////		userSignUpDto.setEmail("JUnitTest@test.com");
////		userSignUpDto.setNickname("JUnitTest");
////		userSignUpDto.setPassword("JUnitTest");
//
//		UserSignUpDto userSignUpDto = userSignUpDto();
//		CommonResult result = authController.signUp(userSignUpDto);
//
//		CommonResult successResult = successResult();
//
//		assertEquals(successResult, result);
//	}
//
//	private UserSignUpDto userSignUpDto() {
//		return UserSignUpDto.builder().email("JUnitTest@test.com").nickname("JUnitTest").password("JUnitTest").build();
//	}
//
//	private CommonResult successResult() {
//		return CommonResult.builder().success(true).code(CommonResponse.SUCCESS.getCode())
//				.msg(CommonResponse.SUCCESS.getMsg()).build();
//	}
//
//	@Test
//	void testValidateNickname() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testLogin() {
//		fail("Not yet implemented");
//	}
//
//}
