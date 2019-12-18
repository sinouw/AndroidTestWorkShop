package com.example.yassineouni.testworkshop.models;

public class Post {

    private String id;
    private String title;
    private String description;

    public Post() {
    }

    public Post(String id,String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
