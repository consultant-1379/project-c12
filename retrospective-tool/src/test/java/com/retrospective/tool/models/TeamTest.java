//package com.retrospective.tool.models;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.HashSet;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//
//public class TeamTest {
//    Team team;
//
//    @Before
//    public void setUp() throws Exception{
//        team = new Team("Team Test", new HashSet<>());
//    }
//
//    @Test
//    public void verifyGetId(){
//        assertNotNull(team.getId());
//        assertEquals(0, team.getId());
//    }
//
//    @Test
//    public void verifyGetName() {
//        assertNotNull(team.getName());
//        assertEquals("Team Test", team.getName());
//    }
//
//    @Test
//    public void verifySetName() {
//        team.setName("Team 8");
//        assertNotNull(team.getName());
//        assertEquals("Team 8", team.getName());
//    }
//
//    @Test
//    public void verifyAddMember(){
//        Member memberOne = new Member("Oscar", "BC", "obc@mail.com","password");
//        TeamMember teamMember=new TeamMember(memberOne);
//        team.addMember(teamMember);
//        assertEquals("Oscar", team.getTeamMembers().iterator().next().getMember().getFirstName());
//    }
//
//    @Test
//    public void verifyRemoveMember(){
//        Member memberOne = new Member("Oscar", "BC", "obc@mail.com","password");
//
//        TeamMember teamMember=new TeamMember(memberOne);
//        team.addMember(teamMember);
//        team.removeMember(teamMember);
//        assertEquals(0, team.getTeamMembers().size());
//    }
//}
