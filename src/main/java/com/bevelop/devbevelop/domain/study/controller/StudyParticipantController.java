package com.bevelop.devbevelop.domain.study.controller;

import com.bevelop.devbevelop.domain.study.dto.ParticipateStudyDto;
import com.bevelop.devbevelop.domain.study.service.StudyParticipantService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/studies/{study-id}/user")
@RequiredArgsConstructor
public class StudyParticipantController {

    private final StudyParticipantService studyParticipantService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "jwt", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PostMapping
    public ResponseEntity<Void> participateStudy(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("study-id") final Long studyId,
            @Valid @RequestBody final ParticipateStudyDto message
    ) {
        studyParticipantService.participateStudy(userDetails, studyId, message);
        return ResponseEntity.noContent().build();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "jwt", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @DeleteMapping
    public ResponseEntity<Void> leaveStudy(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("study-id") final Long studyId
    ) {
        studyParticipantService.leaveStudy(userDetails, studyId);
        return ResponseEntity.noContent().build();
    }



}
