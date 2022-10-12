package com.bevelop.devbevelop.domain.study.controller;

import com.bevelop.devbevelop.domain.study.query.SearchingTags;
import com.bevelop.devbevelop.domain.study.service.SearchingStudyService;
import com.bevelop.devbevelop.domain.study.service.response.StudiesResponse;
import com.bevelop.devbevelop.domain.study.service.response.StudyDetailResponse;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"5. Study Search Controller"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/studies")
public class SearchingStudyController {

    private final SearchingStudyService searchingStudyService;

    @GetMapping
    public ResponseEntity<StudiesResponse> getStudies(
            @PageableDefault(size = 5) final Pageable pageable
    ) {
        final StudiesResponse studiesResponse = searchingStudyService.getStudies(SearchingTags.emptyTags(), pageable);
        return ResponseEntity.ok().body(studiesResponse);
    }

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

//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "jwt", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
//    })
    @GetMapping("/{study-id}")
    public ResponseEntity<StudyDetailResponse> getStudyDetails(
            @PathVariable(name = "study-id") Long studyId) {
        final StudyDetailResponse response = searchingStudyService.getStudyDetails(studyId);
        System.out.println(response);
        return ResponseEntity.ok().body(response);
    }

}
