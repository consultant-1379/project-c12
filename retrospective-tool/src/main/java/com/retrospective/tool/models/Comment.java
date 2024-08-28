package com.retrospective.tool.models;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "employee_id")
    private Member member;
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade=CascadeType.ALL)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    public Comment() {}

    public Comment(Member member, String comment) {
        this.member = member;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String toString(){
        return "Name: "+member.getFullName() + "\nComment: "+ comment;
    }
    public Item getItem() {
        return item;
    }

    public void addItem(Item item) {
        this.item = item;
    }
}
