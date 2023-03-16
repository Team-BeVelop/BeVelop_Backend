package com.bevelop.devbevelop.domain.study.repository;

import com.bevelop.devbevelop.domain.study.domain.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProposalStudyRepository  extends JpaRepository<Proposal, Long> {
}
