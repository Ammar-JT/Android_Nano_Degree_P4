package com.example.chef;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.IdlingResource;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.chef.IdlingResource.SimpleIdlingResource;
import com.example.chef.model.Ingredient;
import com.example.chef.model.Recipe;
import com.example.chef.model.Step;
import com.example.chef.rv_adapter.RecipeAdapter;
import com.example.chef.utilities.RecipeUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends AppCompatActivity implements RecipeAdapter.ListItemClickListener {

    private static final int RECIPE_LIST_ITEMS = 4;
    private String [] recipeJsonStringArray;
    Recipe [] recipeArray;

    private RecipeAdapter mAdapter;
    private RecyclerView mRecipeList;
    private RecipeAdapter.ListItemClickListener globalListener;

    //Simple Idling Resource member variable is needed to test the async task:
    SimpleIdlingResource mIdlingResource;


    public IdlingResource getIdlingResource(){
        if(mIdlingResource==null){
            mIdlingResource = new SimpleIdlingResource();


        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //We need the listener later, so put the value to this global variable:
        globalListener = this;

        //get an instance of idling resource:
        getIdlingResource();




        if(findViewById(R.id.rv_recipes_tablet)!=null){
            //Recycler View reference variables for tablet:
            mRecipeList = findViewById(R.id.rv_recipes_tablet);
            GridLayoutManager layoutManager = new GridLayoutManager(this, 3);

            mRecipeList.setLayoutManager(layoutManager);

        }else {
            //Recycler View reference variable:
            mRecipeList = findViewById(R.id.rv_recipes);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            mRecipeList.setLayoutManager(layoutManager);


        }
        mRecipeList.setHasFixedSize(true);

        //get the api request url in string type
        String apiRequestUrlString = this.getString(R.string.api_request_url);

        //convert it from string to url type:
        URL apiRequestUrl = null;
        try {
            apiRequestUrl = new URL(apiRequestUrlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        new TMDBQueryTask().execute(apiRequestUrl);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //set the idle state to false, to prevent it from testing until you get the data
        //.. from the background thread:
        mIdlingResource.setIdleState(false);
    }

    // Here we used AsyncTask to execute the api request in the background thread:
    public class TMDBQueryTask extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL... params) {
            URL apiURL = params[0];
            String apiRequestResults = null;

            try {
                apiRequestResults = RecipeUtils.getResponseFromHttpUrl(apiURL);
            } catch (IOException e){
                e.printStackTrace();
            }
            return apiRequestResults;
        }

        //after getting the api request results, we override onPostExecute function:
        @Override
        protected void onPostExecute(String apiRequestResults) {
            if (apiRequestResults != null && !apiRequestResults.equals("")) {
                recipeJsonStringArray = RecipeUtils.getJsonStringArray(apiRequestResults);
                recipeArray = new Recipe[recipeJsonStringArray.length];

                for(int x=0; x<recipeJsonStringArray.length; x++){
                    recipeArray[x] = RecipeUtils.parseJsonRecipe(recipeJsonStringArray[x]);
                    String [] jsonIngredientsArray = recipeArray[x].getJsonIngredientsArray();

                    Ingredient[] parsedIngredientsArray = new Ingredient[jsonIngredientsArray.length];
                    for(int y=0; y<jsonIngredientsArray.length; y++){
                        parsedIngredientsArray[y] = RecipeUtils.parseJsonIngredient(jsonIngredientsArray[y]);
                    }

                    recipeArray[x].setIngredients(parsedIngredientsArray);

                    String [] jsonStepsArray = recipeArray[x].getJsonStepsArray();
                    Step[] parsedStepsArray = new Step[jsonStepsArray.length];
                    for(int z=0; z<jsonStepsArray.length; z++){
                        parsedStepsArray[z] = RecipeUtils.parseJsonStep(jsonStepsArray[z]);
                    }
                    recipeArray[x].setSteps(parsedStepsArray);

                }

                //set the adapter:
                mAdapter = new RecipeAdapter(RECIPE_LIST_ITEMS, globalListener, recipeArray);
                mRecipeList.setAdapter(mAdapter);

                //after you get the value from the background, now set the idle state to true:
                mIdlingResource.setIdleState(true);

            }
        }

    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        //We make an intent to move to another activity:
        Intent intent = new Intent(this, DetailActivity.class);

        // here we get the specific movie json string:
        Recipe recipe = recipeArray[clickedItemIndex];

        //we transfer  that single recipe object:
        intent.putExtra("recipe", recipe);

        //start the intent
        startActivity(intent);

    }

}
