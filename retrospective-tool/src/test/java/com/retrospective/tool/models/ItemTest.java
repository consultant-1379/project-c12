package com.retrospective.tool.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemTest {
    private Item item;

    @Before
    public void init(){
        item=new Item("Item1","doNothing");

    }


    @Test
    public void getTitleTest(){
        assertEquals("Item1",item.getTitle());
    }
    @Test
    public void getContents(){
        assertEquals("doNothing",item.getContents());

    }
    @Test
    public void getInitialVotes(){
        assertEquals(0,item.getVotes());
    }
    @Test
    public void upVoteTest(){
        item.upvote();
        assertEquals(1,item.getVotes());
    }
    @Test
    public void downVoteAfterDecrementIfVoteIsZeroInitially(){
        if(item.getVotes()==0){
            item.downvote();
            assertEquals(0,item.getVotes());
        }
    }
    @Test
    public void downVoteIfVoteBiggerThanZeroInitially(){
        item.upvote();
        int vote =item.getVotes();

        if(item.getVotes()>0){
            item.downvote();
            vote=vote-1;
            assertEquals(vote,item.getVotes());
        }
    }
    @Test
    public void getProgressAndSetProgressTest(){

        item.setProgress(Progress.TODO);
        assertEquals(Progress.TODO,item.getProgress());

        item.setProgress(Progress.INPROGRESS);
        assertEquals(Progress.INPROGRESS,item.getProgress());

        item.setProgress(Progress.COMPLETE);
        assertEquals(Progress.COMPLETE,item.getProgress());
    }

    @Test
    public void getCategoryAndSetCategoryTest(){
        item.setCategory(Category.NONE);
        assertEquals(Category.NONE,item.getCategory());
        item.setCategory(Category.GLAD);
        assertEquals(Category.GLAD,item.getCategory());
        item.setCategory(Category.MAD);
        assertEquals(Category.MAD,item.getCategory());
        item.setCategory(Category.SAD);
        assertEquals(Category.SAD,item.getCategory());

    }

    @Test
    public void getCommentAndAddCommentTest(){
        Member member=new Member("Test","Member","testmember@gmail.com","password");
        Comment comment=new Comment(member,"Comment");
        item.addComment(comment);
        String expect="[Name: Test Member\nComment: Comment]";
        assertEquals(expect,item.getComments().toString());
    }

}
