package com.bevelop.devbevelop.domain.study.service;

import com.bevelop.devbevelop.domain.study.domain.Proposal;
import com.bevelop.devbevelop.domain.study.dto.ProposalStudyDto;
import com.bevelop.devbevelop.domain.study.repository.ProposalStudyRepository;
import com.bevelop.devbevelop.domain.user.controller.UserController;
import com.bevelop.devbevelop.domain.user.domain.User;
import com.bevelop.devbevelop.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class StudyProposalService {

    private final ProposalStudyRepository proposalStudyRepository;

    private final UserRepository userRepository;

    private final UserController userController;

    public StudyProposalService(ProposalStudyRepository proposalStudyRepository, UserRepository userRepository, UserController userController) {
        this.proposalStudyRepository = proposalStudyRepository;
        this.userRepository = userRepository;
        this.userController = userController;
    }

    public void proposalStudy(final Long studyId, final String nickname, final ProposalStudyDto message) {
        User owner = userController.getCurrentUser();
        Optional<User> user = userRepository.findByNickname(nickname);
        System.out.println(owner.getId());
        System.out.println(user.get().getId());
        final Proposal proposal = new Proposal(studyId, owner.getId(), user.get().getId(), message.getMessage());
        proposalStudyRepository.save(proposal);
    }
}
