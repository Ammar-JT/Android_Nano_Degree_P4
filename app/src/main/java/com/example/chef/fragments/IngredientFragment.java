package com.example.chef.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.chef.R;
import com.example.chef.database.AppDatabase;
import com.example.chef.database.Favorite;
import com.example.chef.model.Ingredient;



public class IngredientFragment extends Fragment {
    private static final String TAG = IngredientFragment.class.getSimpleName();
    private AppDatabase mDb;

    Ingredient [] ingredients;
    int recipeId;
    String recipeName;

    String text;
    TextView textView;

    Button mButton;

    //constructor:
    public IngredientFragment(Ingredient[] ingredients, int recipeId, String recipeName){
        this.ingredients = ingredients;
        this.recipeId = recipeId;
        this.recipeName = recipeName;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //initialize mDb:
        mDb = AppDatabase.getInstance(container.getContext());

        View rootView = inflater.inflate(R.layout.ingredient_fragment, container, false);

        textView = rootView.findViewById(R.id.ingredients_tv_for_fragment);
        mButton = rootView.findViewById(R.id.add_to_fav_button);

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
        return rootView;
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
