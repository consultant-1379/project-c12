package com.retrospective.tool.models;

import static org.junit.Assert.assertEquals;

import com.retrospective.tool.models.Comment;
import com.retrospective.tool.models.Member;
import org.junit.Before;
import org.junit.Test;

public class CommentTest {
    Member member;
    Comment comment;

    @Before
    public void setUp() {
        member = new Member();
        comment = new Comment();
    }

    @Test
    public void verifySetAndGetMember() {
        // Setting member details
        member.setEmployeeNumber(1);
        member.setEmail("sean@mail.com");
        member.setFirstName("Sean");
        member.setSurname("Stewart");

        comment.setMember(member);
        assertEquals(member, comment.getMember());
    }

    @Test
    public void verifySetAndGetComment() {
        comment.setComment("This is my comment.");
        assertEquals("This is my comment.", comment.getComment());
    }

    @Test
    public void verifyToString() {
        // Setting member details
        member.setEmployeeNumber(1);
        member.setEmail("sean@mail.com");
        member.setFirstName("Sean");
        member.setSurname("Stewart");

        comment.setMember(member);
        comment.setComment("This is my comment.");
        assertEquals("Name: Sean Stewart\nComment: This is my comment.", comment.toString());
    }
}
