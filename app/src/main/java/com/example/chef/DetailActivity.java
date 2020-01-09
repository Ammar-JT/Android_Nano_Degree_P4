package com.example.chef;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chef.fragments.IngredientFragment;
import com.example.chef.fragments.MasterListFragment;
import com.example.chef.fragments.StepFragment;
import com.example.chef.model.Ingredient;
import com.example.chef.model.Recipe;
import com.example.chef.model.Step;

public class DetailActivity extends AppCompatActivity implements MasterListFragment.OnStepClickListener, MasterListFragment.OnDataPass{

    private static final String TAG = "DetailActivity";
    public Recipe recipe;
    private boolean mTwoPane;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recipe = (Recipe) getIntent().getSerializableExtra("recipe");
        Log.d(TAG,"Received this recipe: " + recipe.getName());

        setContentView(R.layout.activity_detail);

    }


    @Override
    public void onStepSelected(int position) {
        if(findViewById(R.id.step_and_ingredients_container)!=null) {
            if (position >= 0) {
                Log.d(TAG, "the step " + (position + 1) + " is clicked form the DetailActivity");

                //let's fill the container with a fragment:
                StepFragment stepFragment = new StepFragment(recipe.getSteps()[position]);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.step_and_ingredients_container, stepFragment)
                        .commit();


            } else {
                Log.d(TAG, "the ingredient button is clicked from DetailActivity");

                //let's fill the container with a fragment:
                IngredientFragment ingredientFragment = new IngredientFragment(recipe.getIngredients(), recipe.getId(), recipe.getName());

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.step_and_ingredients_container, ingredientFragment)
                        .commit();

            }


        }else {
            if (position >= 0) {
                Log.d(TAG, "the step " + (position + 1) + " is clicked form the DetailActivity");

                //We make an intent to move to another activity:
                Intent intent = new Intent(this, StepActivity.class);

                // here we get the specific
                Step step = recipe.getSteps()[position];

                //we transfer  that single recipe object:
                intent.putExtra("step", step);

                //start the intent
                startActivity(intent);
            } else {
                Log.d(TAG, "the ingredient button is clicked from DetailActivity");

                //We make an intent to move to another activity:
                Intent intent = new Intent(this, IngredientActivity.class);

                // here we get the specific
                Ingredient[] ingredients = recipe.getIngredients();

                //we transfer  that single recipe object:
                intent.putExtra("ingredients", ingredients);
                intent.putExtra("recipeId", recipe.getId());
                intent.putExtra("recipeName", recipe.getName());

                //start the intent
                startActivity(intent);
            }
        }


    }

    @Override
    public Step[] passSteps() {
        return recipe.getSteps();
    }

    @Override
    public String passRecipeName() {
        return recipe.getName();
    }



}
