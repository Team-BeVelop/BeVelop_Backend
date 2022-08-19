package com.bevelop.devbevelop.domain.project.dto;

import com.bevelop.devbevelop.domain.project.domain.Project;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    void updateProjectFromDto(ProjectUpdate dto, @MappingTarget Project project);
}