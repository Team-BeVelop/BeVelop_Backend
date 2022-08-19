package com.bevelop.devbevelop.domain.team.service;

import com.bevelop.devbevelop.domain.project.domain.Project;
import com.bevelop.devbevelop.domain.project.dto.ProjectMapper;
import com.bevelop.devbevelop.domain.project.repository.ProjectRepository;
import com.bevelop.devbevelop.domain.team.domain.Team;
import com.bevelop.devbevelop.domain.team.repository.TeamRepository;
import com.bevelop.devbevelop.global.error.ErrorCode;
import com.bevelop.devbevelop.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;

    //팀 생성
    public Team create(Team team) {
        validateDuplicateTitle(team);
        return teamRepository.save(team);
    }

    //중복 제목 체크
    private void validateDuplicateTitle(Team team) {
        Optional<Team> findTeam = teamRepository.findByTitle(team.getTitle());
        if(findTeam.isPresent()) throw new CustomException(ErrorCode.TEAM_EXISTS);
    }
}
