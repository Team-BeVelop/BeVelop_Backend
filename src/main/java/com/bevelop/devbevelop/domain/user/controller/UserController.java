package com.bevelop.devbevelop.domain.user.controller;

import com.bevelop.devbevelop.domain.user.domain.User;
import com.bevelop.devbevelop.domain.user.dto.UserRes;
import com.bevelop.devbevelop.domain.user.exception.UserNotFoundException;
import com.bevelop.devbevelop.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public UserRes profile(@AuthenticationPrincipal UserDetails userDetails) throws UserNotFoundException {
        System.out.println("userDetails = " + userDetails);
        User userDetail = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UserNotFoundException());

        return UserRes.builder()
                .email(userDetail.getEmail())
                .name(userDetail.getName())
                .build();
    }

    @GetMapping("/profile/view/{username}")
    public UserRes userProfile(@PathVariable String username) throws UserNotFoundException {
        User user = userService.findByName(username)
                .orElseThrow(UserNotFoundException::new);

        return UserRes.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    @GetMapping("/userList")
    public List<User> showUserList() {
        return userService.findAll();
    }
}