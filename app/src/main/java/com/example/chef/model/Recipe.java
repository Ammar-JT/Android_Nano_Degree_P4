package com.example.chef.model;

import java.io.Serializable;

public class Recipe implements Serializable {

    private int id;
    private String name;
    private String [] jsonIngredientsArray;
    private String [] jsonStepsArray;
    private int servings;
    private String image;

    private Ingredient [] ingredients;
    private Step [] steps;



    public Recipe(int id, String name, String [] jsonIngredientsArray, String [] jsonStepsArray, int servings, String image) {
        this.id = id;
        this.name = name;
        this.jsonIngredientsArray = jsonIngredientsArray;
        this.jsonStepsArray = jsonStepsArray;
        this.servings = servings;
        this.image = image;
    }

    //next values will be filled from the constructor:
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String[] getJsonIngredientsArray() {
        return jsonIngredientsArray;
    }

    public String[] getJsonStepsArray() {
        return jsonStepsArray;
    }

    public int getServings() {
        return servings;
    }

    public String getimage() {
        return image;
    }



    //Next values will be filled from the from the setter methods:
    public Ingredient[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(Ingredient[] ingredients) {
        this.ingredients = ingredients;
    }

    public Step[] getSteps() {
        return steps;
    }

    public void setSteps(Step[] steps) {
        this.steps = steps;
    }



}
