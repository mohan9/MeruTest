package com.mohan.merutest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.mohan.merutest.R;
import com.mohan.merutest.entity.RecipesData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipesListAdapter extends RecyclerView.Adapter<RecipesListAdapter.RecipesViewHolder> {

    private final LayoutInflater mInflater;
    private List<RecipesData> recipesData;

    public RecipesListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecipesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.row_recipes, parent, false);
        return new RecipesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesViewHolder holder, int position) {
        if (recipesData != null) {
            RecipesData recipes = recipesData.get(position);
            holder.txt_title.setText(recipes.getTitle());
            holder.txt_publisher.setText("By : " + recipes.getPublisher());
            holder.txt_rank.setText("Rank : " + (int) recipes.getSocial_rank());
            Picasso.get().load(recipes.getImage_url()).into(holder.iv_recipes);
        }
    }

    @Override
    public int getItemCount() {
        if (recipesData != null)
            return recipesData.size();
        else return 0;
    }

    public void setRecipesData(List<RecipesData> recipesD) {
        recipesData = recipesD;
        notifyDataSetChanged();
    }


    class RecipesViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView txt_title, txt_publisher, txt_rank;
        private AppCompatImageView iv_recipes;

        private RecipesViewHolder(View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.txt_title);
            txt_publisher = itemView.findViewById(R.id.txt_publisher);
            iv_recipes = itemView.findViewById(R.id.iv_recipes);
            txt_rank = itemView.findViewById(R.id.txt_rank);
        }
    }
}
