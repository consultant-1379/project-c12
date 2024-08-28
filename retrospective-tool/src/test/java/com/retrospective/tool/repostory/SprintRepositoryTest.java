package com.retrospective.tool.repostory;


import com.retrospective.tool.models.Sprint;
import com.retrospective.tool.repositories.SprintRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;



import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class SprintRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    public SprintRepository repo;

    @Test
    public void createSprintTest(){
        Sprint sprint =new Sprint("Name","Goal");
        System.out.println(repo);
        System.out.println(sprint);
        Sprint savedSprint=repo.save(sprint);

        Sprint existSprint=entityManager.find(Sprint.class,savedSprint.getId());

        assertEquals(savedSprint,existSprint);
        repo.delete(sprint);
    }
}
