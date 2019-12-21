package com.example.myapplication;

import java.util.Objects;

public class User {
    String name;
    String score;
    String location;

    public User(){

    }

    public User(String name, String score, String location) {

        this.name = name;
        this.score = score;
        this.location = location;
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
}
