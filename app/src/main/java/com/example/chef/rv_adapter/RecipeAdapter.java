package com.example.chef.rv_adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.chef.R;
import com.example.chef.model.Recipe;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>{

    private static final String TAG = RecipeAdapter.class.getSimpleName();
    final private ListItemClickListener mOnClickListener;
    private static int viewHolderCount;
    private int mNumberItems;
    private Context globalContext;
    private Recipe [] RecipeArray;

    //an interface that will be override in the main activity, to use it when user click at a recipe post:
    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    //recipe adapter constructor:
    public RecipeAdapter(int numberOfItems, ListItemClickListener listener, Recipe[] recipeArray) {
        mNumberItems = numberOfItems;
        mOnClickListener = listener;
        RecipeArray = recipeArray;
        viewHolderCount = 0;
    }



    //when a view holder been created for the first time, it will have the next:
    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //we get the context here:
        globalContext = viewGroup.getContext();

        // get the layout id of the single item:
        int layoutIdForListItem = R.layout.recipe_list_item;

        //inflater:
        LayoutInflater inflater = LayoutInflater.from(globalContext);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        //create a new view holder:
        RecipeViewHolder viewHolder = new RecipeViewHolder(view);

        viewHolderCount++;
        Log.d(TAG, "onCreateViewHolder: number of ViewHolders created: "
                + viewHolderCount);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        Log.d(TAG, "#" + position);
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }


    class RecipeViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        TextView tv;

        public RecipeViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            tv = itemView.findViewById(R.id.info_text);

        }

        /**
         * A method we wrote for convenience. This method will take an integer as input and
         * use that integer to display the appropriate text within a list item.
         * @param listIndex Position of the item in the list
         */
        void bind(int listIndex) {
            //when the view holder been bond, we do the next:
            ///get the recipe json string for a single recipe:
            Recipe recipe =RecipeArray[listIndex];

            tv.setText(recipe.getName());



        }


        /**
         * Called whenever a user clicks on an item in the list.
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);

        }
    }

}
