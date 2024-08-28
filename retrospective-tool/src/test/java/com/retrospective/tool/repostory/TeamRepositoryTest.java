package com.retrospective.tool.repostory;

import com.retrospective.tool.models.Member;
import com.retrospective.tool.models.Team;
import com.retrospective.tool.repositories.TeamRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class TeamRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TeamRepository repo;
    @Test
    public void createTeamTest() {
        Member user = new Member();
        user.setEmail("jian2@gmail.com");
        user.setPassword("1111111");
        user.setFirstName("jian");
        user.setSurname("yan");

        Team team = new Team("TestTeam", Collections.singleton(user));
        Team savedTeam = repo.save(team);

        Team existTeam = entityManager.find(Team.class, savedTeam.getId());

        assertThat(savedTeam).isEqualTo(existTeam);
        repo.delete(team);

    }

    @Test
    public void findByIdTest() {
        Member user = new Member();
        user.setEmail("jian2@gmail.com");
        user.setPassword("1111111");
        user.setFirstName("jian");
        user.setSurname("yan");

        Team team = new Team("TestTeam", Collections.singleton(user));
        Team savedTeam = repo.save(team);

        Team existTeam = repo.findById(savedTeam.getId());

        assertThat(existTeam).isEqualTo(savedTeam);
        repo.delete(team);

    }

    @Test
    public void findByNameTest() {
        Member user = new Member();
        user.setEmail("jian32@gmail.com");
        user.setPassword("1111111");
        user.setFirstName("jian");
        user.setSurname("yan");

        Team team = new Team("TestTeam2", Collections.singleton(user));
        Team savedTeam = repo.save(team);

        Team existTeam = repo.findByName(savedTeam.getName());

        assertThat(existTeam).isEqualTo(savedTeam);
        repo.delete(team);

    }



}
