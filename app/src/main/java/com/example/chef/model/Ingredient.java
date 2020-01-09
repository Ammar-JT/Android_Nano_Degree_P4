package com.example.chef.model;

import java.io.Serializable;

public class Ingredient implements Serializable {
    int quantity;
    String measure;
    String ingredient;

    public Ingredient(int quantity, String measure, String ingredient){
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getIngredient() {
        return ingredient;
    }

    public String getMeasure() {
        return measure;
    }

}
