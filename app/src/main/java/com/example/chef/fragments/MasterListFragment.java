/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.chef.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chef.R;
import com.example.chef.rv_adapter.StepAdapter;
import com.example.chef.model.Step;


// This fragment displays all of the AndroidMe images in one large list
// The list appears as a grid of images
public class MasterListFragment extends Fragment implements StepAdapter.StepClickListener {

    private static final String TAG = "MasterListFragment";
    // Define a new interface OnImageClickListener that triggers a callback in the host activity
    OnStepClickListener mCallback;
    OnDataPass dataPasser;
    private RecyclerView mStepList;
    private StepAdapter mAdapter;

    private StepAdapter.StepClickListener globalListener;
    private Button mIngredientButton;
    private TextView mRecipeName;





    //private StepAdapter mAdapter;

    // OnStepClickListener interface, calls a method in the host activity named onImageSelected
    public interface OnStepClickListener {
        void onStepSelected(int position);
    }

    public interface OnDataPass {
        public Step [] passSteps();
        public String passRecipeName();
    }


    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnStepClickListener) context;
            dataPasser = (OnDataPass) context;
            Log.d(TAG, "The master list fragment is attached with the activity");
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnStepClickListener");
        }
    }


    // Mandatory empty constructor
    public MasterListFragment() {
    }

    // Inflates the GridView of all AndroidMe images
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        globalListener = this;

        final View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);

        Step [] steps  = dataPasser.passSteps();
        String recipeName = dataPasser.passRecipeName();


        Log.d(TAG, "the first step is: " + steps[0].getShortDescription());


        //View reference variables:
        mRecipeName = rootView.findViewById(R.id.recipe_name_tv);
        mIngredientButton = rootView.findViewById(R.id.ingredient_button);
        mStepList = rootView.findViewById(R.id.steps_rv);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mStepList.setLayoutManager(layoutManager);

        mStepList.setHasFixedSize(true);



        mAdapter = new StepAdapter(steps.length,globalListener, steps);


        mStepList.setAdapter(mAdapter);

        mRecipeName.setText(recipeName);

        mIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onStepSelected(-1);
            }
        });


        // Return the root view
        return rootView;
    }

    @Override
    public void onStepClick(int clickedItemIndex) {
        Log.d(TAG, "the step " + (clickedItemIndex+1) + " is clicked form MasterListFragment");

        mCallback.onStepSelected(clickedItemIndex);
    }



}
