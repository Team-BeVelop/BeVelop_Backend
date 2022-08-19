package com.bevelop.devbevelop.domain.user.controller;

import com.bevelop.devbevelop.domain.user.domain.User;
import com.bevelop.devbevelop.domain.user.dto.UserRes;
import com.bevelop.devbevelop.domain.user.service.UserServiceImpl;
import com.bevelop.devbevelop.global.error.exception.CustomException;
import com.bevelop.devbevelop.global.error.ErrorCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {"2. User Controller"})
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @GetMapping("/hello")
    public String hello() {
        return "testing Docker...";
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "현재 유저 프로필", notes = "현재 로그인된 유저의 UserRes")
    @GetMapping("/profile")
    public UserRes profile(@AuthenticationPrincipal UserDetails userDetails) throws CustomException {
        System.out.println("userDetails = " + userDetails);
        User userDetail = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        return UserRes.builder()
                .email(userDetail.getEmail())
                .name(userDetail.getName())
                .build();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "유저 프로필 찾기", notes = "PathVariable로 주어진 username을 가진 유저의 UserRes")
    @GetMapping("/profile/view/{username}")
    public UserRes userProfile(@PathVariable String username) throws CustomException {
        User user = userService.findByName(username)
                .orElseThrow(()->new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        return UserRes.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "모든 유저", notes = "모든 유저의 리스트")
    @GetMapping("/userList")
    public List<User> showUserList() {
        return userService.findAll();
    }
}