package com.example.projetofinal1.models;

import com.google.firebase.firestore.Exclude;

import java.util.List;

public class Restaurant {
    private String name;
    private String imagePath;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
