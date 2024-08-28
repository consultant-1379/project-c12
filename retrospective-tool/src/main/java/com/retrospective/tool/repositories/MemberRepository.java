package com.retrospective.tool.repositories;

import com.retrospective.tool.models.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Member findById(int id);
    @Query("SELECT u FROM Member u WHERE u.email = ?1")
    Member findByEmail(String email);

}