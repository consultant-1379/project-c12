package com.retrospective.tool.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "TEAM_MEMBERS",
            joinColumns = {@JoinColumn(name = "Team_ID")},
            inverseJoinColumns = {@JoinColumn(name = "Member_id")})
    private Set<Member> members;


    public Team(){}

    public Team(String name, Set<Member> members) {
        this.name = name;
        this.members = members;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Member> getMembers() {
        return members;
    }

    public void addMember(Member member){
        members.add(member);
    }

    public void setTeamMembers(Set<Member> teamMembers) {
        this.members = teamMembers;
    }

    public void removeMember(Member member) {
        members.remove(member);
    }
}
