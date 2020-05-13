package com.example.baking.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.baking.R;
import com.example.baking.activity.RecipeStepActivity;
import com.example.baking.activity.bean.RecipePresentationBean;
import com.example.baking.bean.Step;

import java.util.List;


public class MasterListViewAdapter extends RecyclerView.Adapter<MasterListViewAdapter.RecipeViewHolder> {

    final List<Step> mValues;
    final Context mContext;


    public MasterListViewAdapter(Context context, List<Step> values) {
        mValues = values;
        mContext = context;
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.stepDescTxtView);
        }

        void bind(int position) {
            Log.d("Anandhi123", "value" + mValues.get(position).getShortDescription());
            textView.setText(mValues.get(position).getShortDescription());
        }


    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_recipe_step, viewGroup, false);

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {

        holder.bind(position);

    }

    @Override
    public int getItemCount() {

        Log.d("Anandhi", "size" + mValues.size());
        return mValues.size();
    }
}