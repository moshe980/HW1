package com.example.myapplication;

import java.util.Objects;

public class User {
    private int id;
    private String name;
    private String score;
    private String location;


    public User(String name, String score, String location) {

        this.name = name;
        this.score = score;
        this.location = location;
        this.id=this.hashCode();
    }
    public User(){

    }
    public String getName() {
        return name;
    }

    public String getScore() {
        return score;
    }

    public String getLocation() {
        return location;
    }
    public int getId(){
        return id;
    }
}
