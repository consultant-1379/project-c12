package com.retrospective.tool.repostory;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.retrospective.tool.models.Member;
import com.retrospective.tool.repositories.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class MemberRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MemberRepository repo;

    // test methods go below
    @Test
    public void createMemberTest() {
        Member user = new Member();
        user.setEmail("jian2@gmail.com");
        user.setPassword("1111111");
        user.setFirstName("jian");
        user.setSurname("yan");

        Member savedUser = repo.save(user);

        Member existUser = entityManager.find(Member.class, savedUser.getEmployeeNumber());

        assertThat(user.getEmail()).isEqualTo(existUser.getEmail());
        repo.delete(user);

    }
    @Test
    public void findByIdTest() {
        Member user = new Member();
        user.setEmail("jian2@gmail.com");
        user.setPassword("1111111");
        user.setFirstName("jian");
        user.setSurname("yan");

        Member savedUser = repo.save(user);

        Member existUser = repo.findById(savedUser.getEmployeeNumber());

        assertThat(user.getEmail()).isEqualTo(existUser.getEmail());
        repo.delete(user);

    }

    @Test
    public void findByEmailTest(){
        Member user = new Member();
        user.setEmail("jianjie@gmail.com");
        user.setPassword("1111111");
        user.setFirstName("jian");
        user.setSurname("yan");

        Member savedMember = repo.save(user);

        Member actualMember = repo.findByEmail("jianjie@gmail.com");
        assertEquals(savedMember,actualMember);

        repo.delete(user);
    }
}