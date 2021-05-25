package com.example.projetofinal1.models;

public class CartEntry {
    private String userId;
    private String foodId;

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
}
