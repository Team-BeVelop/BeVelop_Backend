package com.bevelop.devbevelop.domain.study.controller;

import com.bevelop.devbevelop.domain.study.service.SearchingStudyService;
import com.bevelop.devbevelop.domain.study.service.response.StudyDetailResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"5. Study Search Controller"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/studies")
public class SearchingStudyController {

    private final SearchingStudyService searchingStudyService;

//    @GetMapping("/search")
//    public ResponseEntity<?> searchStudies(
//            @RequestParam(required = false, name = "division", defaultValue=  "") final String division,
//            @RequestParam(required = false, name = "job", defaultValue = "") final String job,
//            @RequestParam(required = false, name = "field", defaultValue = "") final String field,
//            @PageableDefault(size = 12) final Pageable pageable
//
//    ) {
//        final SearchingTags searchingTags = new SearchingTags(division, job, field);
//        final StudiesResponse studiesResponse = searchingStudyService.getStudies(searchingTags, pageable);
//        return ResponseEntity.ok().body(studiesResponse);
//    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "jwt", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping("/{study-id}")
    public ResponseEntity<StudyDetailResponse> getStudyDetails(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable(name = "study-id") Long studyId) {
        final StudyDetailResponse response = searchingStudyService.getStudyDetails(userDetails, studyId);
        return ResponseEntity.ok().body(response);
    }

}
