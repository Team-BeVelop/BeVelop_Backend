package com.bevelop.devbevelop.domain.project.repository;

import com.bevelop.devbevelop.domain.project.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment save(Comment comment);
}
