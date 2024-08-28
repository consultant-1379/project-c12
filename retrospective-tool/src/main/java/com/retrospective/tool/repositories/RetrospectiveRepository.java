package com.retrospective.tool.repositories;


import com.retrospective.tool.models.Retrospective;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface RetrospectiveRepository extends JpaRepository<Retrospective, Integer> {
    Retrospective findById(int id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Retrospective r set r.locked =:locked where r.id =:id")
    void updateById(@Param("id") int id, @Param("locked") boolean locked);
}
