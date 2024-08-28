package com.retrospective.tool.repositories;

import com.retrospective.tool.models.Item;
import com.retrospective.tool.models.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    Item findById(int id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Item r set r.progress =:progress where r.id =:id")
    void updateById(@Param("id") int id, @Param("progress") Progress progress);


}
