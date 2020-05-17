package com.example.baking.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.baking.R;
import com.example.baking.activity.RecipeInstructionActivity;
import com.example.baking.activity.RecipeStepActivity;
import com.example.baking.activity.bean.RecipePresentationBean;
import com.example.baking.adapter.MasterListViewAdapter;
import com.example.baking.adapter.RecipeRecyclerViewAdapter;
import com.example.baking.bean.Ingredient;
import com.example.baking.bean.Step;
import com.example.baking.utils.ApplicationConstants;

import java.util.ArrayList;
import java.util.List;


// This fragment displays all of the AndroidMe images in one large list
// The list appears as a grid of images
public class MasterListFragment extends Fragment implements MasterListViewAdapter.ListItemClickListener{

    String ingredientDesc = "";
    RecipePresentationBean recipePresentationBean;
    Step step;
    int itemClicked;

    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception

    }


    // Mandatory empty constructor
    public MasterListFragment() {
    }

    // Inflates the GridView of all AndroidMe images
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);

        // Get a reference to the GridView in the fragment_master_list xml layout file
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_step_desc);

        TextView ingredientTextView = (TextView) rootView.findViewById(R.id.ingredientTxtView);

        recipePresentationBean = getActivity().getIntent().getParcelableExtra(ApplicationConstants.RECIPE);

        List<Ingredient> ingredients = recipePresentationBean.getIngredients();
        for (Ingredient ingredient : ingredients) {

            ingredientDesc = ApplicationConstants.formatNumber(ingredient.getQuantity()) + " " + ingredient.getMeasure() + " " + ingredient.getName();
            ingredientTextView.append(ingredientDesc + "\n");
        }
        // Create the adapter
        // This adapter takes in the context and an ArrayList of ALL the image resources to display
        MasterListViewAdapter mAdapter = new MasterListViewAdapter(getContext(), recipePresentationBean.getSteps(),this);

        // Set the adapter on the GridView
        recyclerView.setAdapter(mAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(linearLayoutManager);

        // Set a click listener on the gridView and trigger the callback onImageSelected when an item is clicked
        // Return the root view
        return rootView;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
       Log.d("Anandhi","clickedItemIndex" +clickedItemIndex);
        if(!((RecipeStepActivity)getActivity()).ismTwoPane()) {
            step = recipePresentationBean.getSteps().get(clickedItemIndex);
            Intent intent = new Intent(getContext(), RecipeInstructionActivity.class);
            intent.putExtra(ApplicationConstants.RECIPE, recipePresentationBean);
            intent.putExtra(ApplicationConstants.STEP, step);
            intent.putExtra(ApplicationConstants.TOTAL_STEPS, recipePresentationBean.getSteps().size());
            intent.putExtra(ApplicationConstants.SELECTED_INDEX, clickedItemIndex);

            startActivity(intent);
        }
        else {
            RecipeInstructionFragment recipeInstructionFragment = new RecipeInstructionFragment(true);

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            recipeInstructionFragment.setRecipePresentationBean(recipePresentationBean);
            recipeInstructionFragment.setSelectedIndex(clickedItemIndex);
            Log.d("pass step","step"+recipePresentationBean.getSteps().get(clickedItemIndex));
            recipeInstructionFragment.setStep(recipePresentationBean.getSteps().get(clickedItemIndex));
            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_instruction_fragment, recipeInstructionFragment)
                    .commit();
        }
    }

}
