package com.bevelop.devbevelop.domain.user.controller;


import com.bevelop.devbevelop.domain.user.query.SearchingTags;
import com.bevelop.devbevelop.domain.user.service.SearchingUserService;
import com.bevelop.devbevelop.domain.user.service.response.UsersResponse;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"User Search Controller"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class SearchingUserController {

    private final SearchingUserService searchingUserService;

    @GetMapping("/abc")
    public ResponseEntity<UsersResponse> getUsers(
            @PageableDefault(size = 12) final Pageable pageable
            ) {
        final UsersResponse usersResponse = searchingUserService.getUsers(SearchingTags.emptyTags(), pageable);
        return ResponseEntity.ok().body(usersResponse);
    }
}
