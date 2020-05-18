package com.example.baking.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.baking.R;
import com.example.baking.bean.Step;

import java.util.List;


public class MasterListViewAdapter extends RecyclerView.Adapter<MasterListViewAdapter.RecipeViewHolder>  {

    final List<Step> mValues;
    final Context mContext;
    int selectedPosition=-1;

    private final MasterListViewAdapter.ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {

        void onListItemClick(int clickedItemIndex);

    }


    public MasterListViewAdapter(Context context, List<Step> values, MasterListViewAdapter.ListItemClickListener listener) {
        mValues = values;
        mOnClickListener = listener;
        mContext = context;
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView textView;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.stepDescTxtView);
            itemView.setOnClickListener(this);
        }

        void bind(int position) {
            Log.d("Anandhi123", "value" + mValues.get(position).getShortDescription());
            textView.setText(mValues.get(position).getShortDescription());
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            selectedPosition=clickedPosition;
            mOnClickListener.onListItemClick(clickedPosition);
            notifyDataSetChanged();
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
        if(selectedPosition==position)
            holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        else
            holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorCake));
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
      //  Log.d("Anandhi", "size" + mValues.size());
        return mValues.size();
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }
}