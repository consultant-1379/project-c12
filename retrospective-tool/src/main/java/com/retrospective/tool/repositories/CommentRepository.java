package com.retrospective.tool.repositories;

import com.retrospective.tool.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Comment findById(int id);
}
