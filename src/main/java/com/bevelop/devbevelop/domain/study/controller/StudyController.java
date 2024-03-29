package com.bevelop.devbevelop.domain.study.controller;


import com.bevelop.devbevelop.domain.study.domain.Study;
import com.bevelop.devbevelop.domain.study.dto.StudyRequest;
import com.bevelop.devbevelop.domain.study.service.StudyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@Api(tags = {"4. Study Controller"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/studies")
@Controller
public class StudyController {

    private final StudyService studyService;


    @ApiOperation(value = "스터디 생성", notes = "스터디 생성")
    @PostMapping
    public ResponseEntity<Void> createStudy(
            @Valid @RequestBody final StudyRequest studyRequest
    ) {
        Study study = studyService.createStudy(studyRequest);
        return ResponseEntity.created(URI.create("/studies/"+ study.getId())).build();
    }

    @ApiOperation(value = "스터디 수정", notes = "스터디 수정")
    @PutMapping("/{study-id}")
    public ResponseEntity<Void> updateStudy(
            @PathVariable("study-id") final Long studyId,
            @Valid @RequestBody final StudyRequest studyRequest
    ) {
        studyService.updateStudy(studyId, studyRequest);
        return ResponseEntity.ok().build();
    }
}
