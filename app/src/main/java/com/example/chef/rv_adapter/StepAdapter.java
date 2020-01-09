package com.example.chef.rv_adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chef.R;
import com.example.chef.model.Step;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder>{

    private static final String TAG = StepAdapter.class.getSimpleName();
    private final int mNumberItems;
    private final Step[] mSteps;
    private  int viewHolderCount;
    private Context globalContext;

    private StepAdapter.StepClickListener stepClickListener;


    public StepAdapter(int length, StepClickListener listener, Step[] steps) {
        mNumberItems = length;
        mSteps = steps;
        stepClickListener = listener;
        viewHolderCount = 0;
    }

    public interface StepClickListener {
        void onStepClick(int clickedItemIndex);
    }


    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        //we get the context here:
        globalContext = viewGroup.getContext();

        // get the layout id of the single item:
        int layoutIdForListItem = R.layout.step_list_item;

        //inflater:
        LayoutInflater inflater = LayoutInflater.from(globalContext);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        //create a new view holder:
        StepViewHolder viewHolder = new StepViewHolder(view);

        viewHolderCount++;
        Log.d(TAG, "onCreateViewHolder: number of ViewHolders created: "
                + viewHolderCount);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        Log.d(TAG, "#" + position);
        holder.bind(position);

    }


    @Override
    public int getItemCount() {
        return mNumberItems;
    }


    class StepViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{
        TextView tv;

        public StepViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv = itemView.findViewById(R.id.step_short_description_tv);

        }

        void bind(int listIndex) {
            //when the view holder been bond, we do the next:
            ///get the recipe json string for a single recipe:
            Step step =mSteps[listIndex];


            tv.setText("Step " + (listIndex+1) + ": " + step.getShortDescription());

        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            Log.d(TAG, "the step " + (clickedPosition+1) + " is clicked form the step adapter");
            stepClickListener.onStepClick(clickedPosition);

        }


    }


}
