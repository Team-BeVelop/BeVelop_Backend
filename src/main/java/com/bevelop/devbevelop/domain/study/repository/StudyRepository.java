package com.bevelop.devbevelop.domain.study.repository;

import com.bevelop.devbevelop.domain.study.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {
}
