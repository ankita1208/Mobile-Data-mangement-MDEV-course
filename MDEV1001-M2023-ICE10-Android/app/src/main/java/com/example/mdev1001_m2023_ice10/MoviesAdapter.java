package com.example.mdev1001_m2023_ice10;

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

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    private List<MovieDTO> movieDTOList;
    private CustomClickListener customClickListener;

    public MoviesAdapter(List<MovieDTO> itemList,CustomClickListener customClickListener ) {
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
        holder.ratingTextView.setText(String.valueOf(movieDTO.getCriticsRating()));

        // Check for null before parsing the rating as float
        if (movieDTO.getCriticsRating() != null) {
            holder.ratingTextView.setText(String.valueOf(movieDTO.getCriticsRating()));

            float criticsRating = Float.parseFloat(String.valueOf(movieDTO.getCriticsRating()));
            if (criticsRating > 7) {
                holder.ratingTextView.setBackgroundColor(Color.GREEN);
                holder.ratingTextView.setTextColor(Color.BLACK);
            } else if (criticsRating > 5) {
                holder.ratingTextView.setBackgroundColor(Color.YELLOW);
                holder.ratingTextView.setTextColor(Color.BLACK);
            } else {
                holder.ratingTextView.setBackgroundColor(Color.RED);
                holder.ratingTextView.setTextColor(Color.WHITE);
            }
        } else {
            // Set a default value or handle null case accordingly
            holder.ratingTextView.setText("N/A");
            holder.ratingTextView.setBackgroundColor(Color.GRAY);
            holder.ratingTextView.setTextColor(Color.BLACK);
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