package com.example.chef.database;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "favorite")
public class Favorite {
    @PrimaryKey
    private int id;
    private String recipeName;
    private String ingredients;

    @Ignore
    public Favorite(String recipeName, String ingredients) {
        this.recipeName = recipeName;
        this.ingredients = ingredients;
    }

    public Favorite(int id, String recipeName, String ingredients) {
        this.id = id;
        this.recipeName = recipeName;
        this.ingredients = ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public int getId() {
        return id;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getRecipeName() {
        return recipeName;
    }
}


