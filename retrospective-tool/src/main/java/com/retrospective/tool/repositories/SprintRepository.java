package com.retrospective.tool.repositories;

import com.retrospective.tool.models.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SprintRepository extends JpaRepository<Sprint, Integer> {
}
