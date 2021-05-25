package com.example.projetofinal1.models;

import com.google.firebase.firestore.Exclude;

public class CartEntry {
    private String userId;
    private String foodId;

    private String id;
    private Food food;

    public CartEntry(){}

    public CartEntry(String userId, String foodId){
        this.userId = userId;
        this.foodId = foodId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }


    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Exclude
    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }
}
