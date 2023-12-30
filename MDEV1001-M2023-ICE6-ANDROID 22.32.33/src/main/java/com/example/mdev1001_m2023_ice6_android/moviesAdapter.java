package com.example.mdev1001_m2023_ice6_android;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class moviesAdapter extends RecyclerView.Adapter<moviesAdapter.ViewHolder> {
    private List<MovieDTO> movieDTOList;
    private CustomClickListener customClickListener;

    public moviesAdapter(List<MovieDTO> itemList, CustomClickListener customClickListener) {
        this.movieDTOList = itemList;
        this.customClickListener = customClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_movies, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieDTO movieDTO = movieDTOList.get(position);
        holder.titleTextView.setText(movieDTO.getTitle());
        holder.studioTextView.setText(movieDTO.getStudio());
        holder.ratingTextView.setText(movieDTO.getCriticsRating());

        if (Float.parseFloat(movieDTO.getCriticsRating()) > 7) {
            holder.ratingTextView.setBackgroundColor(Color.GREEN);
            holder.ratingTextView.setTextColor(Color.BLACK);
        } else if (Integer.parseInt(movieDTO.getCriticsRating()) > 5) {
            holder.ratingTextView.setBackgroundColor(Color.YELLOW);
            holder.ratingTextView.setTextColor(Color.BLACK);
        } else {
            holder.ratingTextView.setBackgroundColor(Color.RED);
            holder.ratingTextView.setTextColor(Color.WHITE);
        }

        holder.parentCL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customClickListener.onItemClick(position, 0);
            }
        });

        holder.deleteIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customClickListener.onItemClick(position, 1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return movieDTOList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView studioTextView;
        TextView ratingTextView;
        ConstraintLayout parentCL;
        ImageView deleteIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTV);
            studioTextView = itemView.findViewById(R.id.studioTV);
            ratingTextView = itemView.findViewById(R.id.ratingTV);
            parentCL = itemView.findViewById(R.id.parentCL);
            deleteIV = itemView.findViewById(R.id.deleteIV);
        }
    }
}

