package com.b12.game.adapters;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.b12.game.R;
import com.b12.game.getset.Level;

import java.util.ArrayList;

public class FirstGameLevelsAdapter extends RecyclerView.Adapter<FirstGameLevelsAdapter.FirstGameLevelsViewHolder> {
    ArrayList<Level> list;
    Context context;
    OnLevelClickListener onLevelClickListener;
    SharedPreferences sharedPreferences;

    public FirstGameLevelsAdapter(ArrayList<Level> list, Context context, OnLevelClickListener onLevelClickListener) {
        this.list = list;
        this.context = context;
        this.onLevelClickListener = onLevelClickListener;
    }

    @NonNull
    @Override
    public FirstGameLevelsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.levels_item, parent, false);
        return new FirstGameLevelsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FirstGameLevelsViewHolder holder, int position) {
        sharedPreferences = context.getSharedPreferences("STATUS", MODE_PRIVATE);
        boolean levelStatus = sharedPreferences.getBoolean(Integer.toString(position), true);
        Level level = list.get(position);
        if (levelStatus) {
            holder.textView.setText(level.getLevelNumber());
        } else {
            holder.imageView2.setImageResource(R.drawable.lock);
            holder.textView.setVisibility(View.GONE);
            holder.imageView.setVisibility(View.GONE);
        }
        holder.imageView.setImageResource(level.getLevelStars());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLevelClickListener.onLevelClicked(level.getLevelNumber(), level.isStatus());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class FirstGameLevelsViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageView, imageView2;
        TextView textView;
        LinearLayout layout;

        public FirstGameLevelsViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.levels_card);
            imageView = itemView.findViewById(R.id.levels_stars);
            imageView2 = itemView.findViewById(R.id.levels_lock_img);
            textView = itemView.findViewById(R.id.levels_number);
            layout = itemView.findViewById(R.id.levels_linear);

        }
    }

    public interface OnLevelClickListener {
        void onLevelClicked(String level, boolean status);
    }


}
