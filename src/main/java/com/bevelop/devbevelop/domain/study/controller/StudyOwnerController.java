package com.bevelop.devbevelop.domain.study.controller;


import com.bevelop.devbevelop.domain.study.service.StudyParticipantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"7. Study Accept Refuse Controller"})
@RestController
@RequestMapping("/studies/{study-id}/owner")
@RequiredArgsConstructor
public class StudyOwnerController {

    private final StudyParticipantService studyParticipantService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "jwt", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PutMapping("/accept/{user-id}")
    public ResponseEntity<Void> acceptStudy(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("study-id") final Long studyId,
            @PathVariable("user-id") final Long userId
    ) {
        studyParticipantService.acceptStudy(userDetails, studyId, userId);
        return ResponseEntity.noContent().build();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "jwt", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PutMapping("/refuse/{user-id}")
    public ResponseEntity<Void> refuseStudy(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("study-id") final Long studyId,
            @PathVariable("user-id") final Long userId
    ) {
        studyParticipantService.refuseStudy(userDetails, studyId, userId);
        return ResponseEntity.noContent().build();
    }
}
