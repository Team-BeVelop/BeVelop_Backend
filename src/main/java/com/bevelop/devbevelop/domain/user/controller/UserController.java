package com.bevelop.devbevelop.domain.user.controller;

import com.bevelop.devbevelop.domain.user.domain.User;
import com.bevelop.devbevelop.domain.user.service.UserServiceImpl;
import com.bevelop.devbevelop.global.error.exception.CustomException;
import com.bevelop.devbevelop.global.error.ErrorCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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

//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "jwt", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
//    })
    @ApiOperation(value = "현재 유저 프로필", notes = "현재 로그인된 유저의 정보")
    @GetMapping("/profile")
    public User profile() throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailOrSocialId = authentication.getName();
        User user = (emailOrSocialId.contains("@"))?
                userService.findByEmail(emailOrSocialId).orElseThrow(()-> new CustomException(ErrorCode.MEMBER_NOT_FOUND)):
                userService.findBySocialId(emailOrSocialId).orElseThrow(()-> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        return user;
    }

//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "jwt", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
//    })
    @ApiOperation(value = "유저 프로필 찾기", notes = "PathVariable로 주어진 username을 가진 유저의 정보")
    @GetMapping("/profile/view/{username}")
    public User userProfile(@PathVariable String username) throws CustomException {
        User user = userService.findByName(username)
                .orElseThrow(()->new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        return user;
    }

//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "jwt", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
//    })
    @ApiOperation(value = "모든 유저", notes = "모든 유저의 리스트")
    @GetMapping("/userList")
    public List<User> showUserList() {
        return userService.findAll();
    }
}