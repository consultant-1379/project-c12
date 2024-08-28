package com.retrospective.tool.models;

import java.util.List;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "retrospectives")
public class Retrospective {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id")
    private Team team;
    private Boolean locked;

    @OneToMany(mappedBy = "retrospective", cascade = CascadeType.ALL)
    private Set<Item> items = new HashSet<>();

    public Retrospective() {}

    public int getId() {
        return id;
    }


    public Retrospective(Team team) {
        this.team = team;
        this.locked = false;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        items.add(item);
        item.setRetrospective(this);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public double getProgress(){
        double count = 0;
        for(Item item : items){
            if(item.getProgress() == Progress.COMPLETE){
                count++;
            }
        }
        return count / items.size() * 100;
    }

    public Team getTeam() {
        return team;
    }

}
