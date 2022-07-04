package com.b12.game.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.b12.game.R;

import java.util.ArrayList;
import java.util.Collections;

public class FirstGameAdapter extends RecyclerView.Adapter<FirstGameAdapter.FirstGameViewHolder> {

    ArrayList<Integer> gameItems;

    public FirstGameAdapter(ArrayList<Integer> gameItems) {
        this.gameItems = gameItems;
    }

    @NonNull
    @Override
    public FirstGameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.first_game_item_layout, parent, false);
        return new FirstGameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FirstGameViewHolder holder, int position) {
        holder.rasm.setImageResource(gameItems.get(position));
    }

    @Override
    public int getItemCount() {
        return gameItems.size();
    }

    public class FirstGameViewHolder extends RecyclerView.ViewHolder {
        ImageView rasm;

        public FirstGameViewHolder(@NonNull View itemView) {
            super(itemView);
            rasm = itemView.findViewById(R.id.first_game_img);
        }
    }
}