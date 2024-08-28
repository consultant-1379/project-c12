package com.retrospective.tool.models;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String contents;
    private int votes;
    private Progress progress;
    private Category category;
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade=CascadeType.ALL)
    @JoinColumn(name = "retrospective_id", nullable = false)
    private Retrospective retrospective;

    public Item() {}

    public Item(String title, String contents) {
        this.title = title;
        this.contents = contents;
        this.votes = 0;
        this.progress = Progress.TODO;
        this.category = Category.NONE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getVotes() {
        return votes;
    }

    public void upvote(){
        votes++;
    }

    public void downvote(){
        if(votes>0)votes--;
    }

    public Progress getProgress() {
        return progress;
    }

    public void setProgress(Progress progress) {
        this.progress = progress;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.addItem(this);
    }

    public Retrospective getRetrospective() {
        return retrospective;
    }

    public void setRetrospective(Retrospective retrospective) {
        this.retrospective = retrospective;
    }
}
