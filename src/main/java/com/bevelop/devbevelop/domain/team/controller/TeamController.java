package com.bevelop.devbevelop.domain.team.controller;

import com.bevelop.devbevelop.domain.team.domain.Team;
import com.bevelop.devbevelop.domain.team.dto.TeamForm;
import com.bevelop.devbevelop.domain.team.service.TeamService;
import com.bevelop.devbevelop.global.error.ErrorCode;
import com.bevelop.devbevelop.global.error.exception.CustomException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Api(tags = {"3. Team Controller"})
@RestController
@RequestMapping("/team")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;

    @Transactional
    @ApiOperation(value = "새로운 팀 생성")
    @PostMapping("/new")
    public Team createTeam(@Valid TeamForm teamFormDto) throws CustomException {
        Team team = teamFormDto.toEntity();
        return teamService.create(team);
    }

}
