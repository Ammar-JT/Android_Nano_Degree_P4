package com.example.chef;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.chef.database.AppDatabase;
import com.example.chef.database.Favorite;
import com.example.chef.fragments.AppExecutors;
import com.example.chef.model.Ingredient;


public class IngredientActivity extends AppCompatActivity {

    private static final String TAG = IngredientActivity.class.getSimpleName();
    private AppDatabase mDb;


    Ingredient [] ingredients;
    int recipeId;
    String recipeName;

    String text="";
    TextView textView;
    Button mButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        mDb = AppDatabase.getInstance(getApplicationContext());

        ingredients = (Ingredient[]) getIntent().getSerializableExtra("ingredients");
        recipeId = getIntent().getIntExtra("recipeId",-1);
        recipeName = getIntent().getStringExtra("recipeName");


        textView = findViewById(R.id.ingredients_tv);
        mButton = findViewById(R.id.add_to_fav_button);

        for(int x=0; x<ingredients.length; x++){
            text = text + "- " + ingredients[x].getQuantity() +
                    " " +ingredients[x].getMeasure() +
                    " of " +ingredients[x].getIngredient() + ".\n";
        }

        textView.setText(text);

        LiveData<Favorite> isFavorite = mDb.favoriteDao().loadFavoriteById(recipeId);
        Log.d(TAG, "" + isFavorite);

        isFavorite.observe(this, new Observer<Favorite>() {
            @Override
            public void onChanged(Favorite favorite) {
                final boolean recipeExistInDB;

                if(favorite!=null){
                    Log.d(TAG, "this recipe exist in the database");
                    recipeExistInDB = true;
                    mButton.setText("Remove from Widget");
                    mButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }else{
                    Log.d(TAG, "This recipe doesn't exist in the database!");
                    recipeExistInDB = false;
                    mButton.setText("Display in Widget");
                    mButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));


                }

                mButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onFavButtonClicked(recipeExistInDB);
                    }
                });

            }
        });

    }

    public void onFavButtonClicked(final boolean recipeExistInDB) {


        final Favorite favorite = new Favorite(recipeId,recipeName ,text);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if(recipeExistInDB){
                    mDb.favoriteDao().deleteFavorite(favorite);
                }else{
                    mDb.favoriteDao().deleteAll();
                    mDb.favoriteDao().insertFavorite(favorite);
                }

            }

        });
    }




}
