package com.example.chef.utilities;/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.net.Uri;
import android.util.Log;

import com.example.chef.model.Ingredient;
import com.example.chef.model.Recipe;
import com.example.chef.model.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the network.
 */
public class RecipeUtils {

    public static String getResponseFromHttpUrl(URL url) throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }


    public static String [] getJsonStringArray(String recipeJsonString) {

        String [] recipeJsonStringArray = null;
        try{
            JSONArray recipeJsonArray = new JSONArray(recipeJsonString);

            //here we convert the json array into regular array:
            recipeJsonStringArray = new String[recipeJsonArray.length()];
            for(int i=0; i<recipeJsonArray.length(); i++) {
                recipeJsonStringArray[i]=recipeJsonArray.optString(i);
            }

        }catch (JSONException e) {

            Log.e("RecipeUtils", "Problem getting the Favorite array", e);
        }
        return recipeJsonStringArray;
    }

    public static Recipe parseJsonRecipe(String jsonRecipe) {
        Recipe parsedRecipe = null;
        try{
            //First, Initialize JSON Object from the Movie Json String:
            JSONObject recipeJsonObject = new JSONObject(jsonRecipe);


            //then we get all the values from this json object:
            int id = recipeJsonObject.getInt("id");
            String name = recipeJsonObject.getString("name");

            JSONArray ingredientJsonArray = recipeJsonObject.getJSONArray("ingredients");
            JSONArray stepJsonArray = recipeJsonObject.getJSONArray("steps");

            int servings = recipeJsonObject.getInt("servings");
            String image = recipeJsonObject.getString("image");


            //here we convert the json array into regular array:
            String [] ingredientJsonStringArray = new String[ingredientJsonArray.length()];
            for(int i=0; i<ingredientJsonArray.length(); i++) {
                ingredientJsonStringArray[i]=ingredientJsonArray.optString(i);
            }

            String [] stepJsonStringArray = new String[stepJsonArray.length()];
            for(int i=0; i<stepJsonArray.length(); i++) {
                stepJsonStringArray[i]=stepJsonArray.optString(i);
            }


            parsedRecipe = new Recipe(id, name, ingredientJsonStringArray,stepJsonStringArray,servings,image);
            Log.e("RecipeUtils", "PARSING SUCCEED!!!");
        }catch (JSONException e) {

            Log.e("RecipeUtils", "Problem parsing the Favorite JSON Object!!!", e);
        }

        return parsedRecipe;
    }

    public static Ingredient parseJsonIngredient(String jsonIngredient) {
        Ingredient parsedIngredient = null;

        try {
            JSONObject ingObject = new JSONObject(jsonIngredient);
            int quantity = ingObject.getInt("quantity");
            String measure = ingObject.getString("measure");
            String ingredient = ingObject.getString("ingredient");

            parsedIngredient = new Ingredient(quantity,measure,ingredient);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return parsedIngredient;
    }


    public static Step parseJsonStep(String jsonStep) {
        Step parsedStep = null;

        try {
            JSONObject stepObject = new JSONObject(jsonStep);

            int id = stepObject.getInt("id");

            String shortDescription = stepObject.getString("shortDescription");
            String description = stepObject.getString("description");
            String videoURL = stepObject.getString("videoURL");
            String thumbnailURL = stepObject.getString("thumbnailURL");

            parsedStep = new Step(id, shortDescription, description, videoURL, thumbnailURL);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return parsedStep;
    }
}