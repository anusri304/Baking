package com.example.baking.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.baking.R;
import com.example.baking.activity.RecipeStepActivity;
import com.example.baking.activity.bean.RecipePresentationBean;
import com.example.baking.transport.RecipeTransportBean;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;


public class RecipeRecyclerViewAdapter extends RecyclerView.Adapter<RecipeRecyclerViewAdapter.RecipeViewHolder> {

    final List<RecipePresentationBean> mValues;
    final Context mContext;
    private final ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {

        void onListItemClick(int clickedItemIndex);

    }


    public RecipeRecyclerViewAdapter(Context context, List<RecipePresentationBean> values, ListItemClickListener listener) {
        mValues = values;
        mOnClickListener = listener;
        mContext = context;
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView textView;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(this);

        }

        void bind(int position) {
            Log.d("Anandhi", "value" + mValues.get(position).getName());
            textView.setText(mValues.get(position).getName());
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_recipe, viewGroup, false);

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