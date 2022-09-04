package com.bevelop.devbevelop.domain.study.controller;

import com.bevelop.devbevelop.domain.study.service.SearchingStudyService;
import com.bevelop.devbevelop.domain.study.service.response.StudyDetailResponse;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{study-id}")
    public ResponseEntity<StudyDetailResponse> getStudyDetails(@PathVariable(name = "study-id") Long studyId) {
        final StudyDetailResponse response = searchingStudyService.getStudyDetails(studyId);
        return ResponseEntity.ok().body(response);
    }

}
