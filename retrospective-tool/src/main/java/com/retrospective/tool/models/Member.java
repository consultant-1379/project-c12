package com.retrospective.tool.models;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeeNumber;
    private String firstName;
    private String surname;
    private String email;


    private  String password;

//    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
//    private Set<TeamMember> teamMembers = new HashSet<>();

    public Member() {}

    public Member(String firstName, String surname, String email,String password) {
        this.firstName = firstName;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    public int getEmployeeNumber(){
        return employeeNumber;
    }

    public void setEmployeeNumber(int employeeNumber){
        this.employeeNumber = employeeNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFullName(){
        return firstName + " " + surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public Set<TeamMember> getTeamMembers() {
//        return teamMembers;
//    }
//
//    public void setTeamMembers(Set<TeamMember> teamMembers) {
//        this.teamMembers = teamMembers;
//    }
}
