package com.retrospective.tool.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MemberTest {
    private Member member;

    @Before
    public void init(){
        member=new Member("jian","yan","jian@gmail.com","password");
    }

    @Test
    public void getEmployeeNumberTest(){
        member.setEmployeeNumber(12345);
        assertEquals(12345,member.getEmployeeNumber());
    }

    @Test
    public void getFirstNameTest(){
        member.setFirstName("TestFirstName");
        assertEquals("TestFirstName",member.getFirstName());
    }

    @Test
    public void getSurnameTest(){
        member.setSurname("Yan");
        assertEquals("Yan",member.getSurname());
    }
    @Test
    public void getFullNameTest(){
        member.setFirstName("jian");
        member.setSurname("Yan");

        assertEquals("jian Yan",member.getFullName());
    }

    @Test
    public void getEmailTest(){
        member.setEmail("Something@gmail.com");
        assertEquals("Something@gmail.com",member.getEmail());
    }

}
