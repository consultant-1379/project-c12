package com.retrospective.tool.repositories;

import com.retrospective.tool.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TeamRepository extends JpaRepository<Team, Integer> {
    Team findById(int id);
    @Query("SELECT u FROM Team u WHERE u.name = ?1")
    Team findByName(String name);


}