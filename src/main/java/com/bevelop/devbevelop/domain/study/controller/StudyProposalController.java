package com.bevelop.devbevelop.domain.study.controller;


import com.bevelop.devbevelop.domain.study.dto.ProposalStudyDto;
import com.bevelop.devbevelop.domain.study.service.StudyProposalService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = {"8. Study Proposal Controller"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/studies")
public class StudyProposalController {

    private final StudyProposalService studyProposalService;

    @PostMapping("/proposal/{study-id}/{nickname}")
    public ResponseEntity<Void> proposalStudy(
            @PathVariable("study-id") final Long studyId,
            @PathVariable("nickname") final String nickname,
            @Valid @RequestBody final ProposalStudyDto message) {
       studyProposalService.proposalStudy(studyId, nickname, message);
       return ResponseEntity.noContent().build();
    }

}
