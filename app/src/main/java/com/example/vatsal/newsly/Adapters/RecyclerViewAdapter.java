package com.example.vatsal.newsly.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vatsal.newsly.DatabaseOperations.AppDatabase;
import com.example.vatsal.newsly.DatabaseOperations.DatabaseInstance;
import com.example.vatsal.newsly.R;
import com.example.vatsal.newsly.WebPageActivity;

import java.util.ArrayList;
import java.util.List;

public abstract class RecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    AppDatabase db;
    List<T> dataset;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.recycler_view_row, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        CardView cardView;
        TextView description;
        Button readFull;
        View view;
        boolean isFront;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            textView = view.findViewById(R.id.textView);
            imageView = view.findViewById(R.id.imageView);
            cardView = view.findViewById(R.id.card_view);
            description = view.findViewById(R.id.description);
            readFull = view.findViewById(R.id.button);
            isFront = true;
        }
    }

    protected void setDb() {
        db = DatabaseInstance.getInstance(context);
    }

    protected void showRear(ViewHolder holder) {
        holder.isFront = false;
        holder.textView.setAlpha(0f);
        holder.imageView.setAlpha(0f);
        holder.description.setAlpha(1f);
        holder.readFull.setAlpha(1f);
        holder.readFull.setClickable(true);
    }

    protected void showFront(ViewHolder holder) {
        holder.isFront = true;
        holder.textView.setAlpha(1f);
        holder.imageView.setAlpha(1f);
        holder.description.setAlpha(0f);
        holder.readFull.setAlpha(0f);
        holder.readFull.setClickable(false);
    }

    void puttingDataOnCard(ViewHolder holder, String title, String description, String url, String urlToImage) {
        holder.textView.setText(title);
        Glide.with(context)
                .load(urlToImage)
                .into(holder.imageView);
        showFront(holder);
        holder.description.setText(description);
        holder.readFull.setOnClickListener((View view) -> {
            Intent intent = new Intent(context, WebPageActivity.class);
            intent.putExtra("webPage", url);
            context.startActivity(intent);
        });
    }

    void handleCardFlip(ViewHolder holder) {
        holder.cardView.setOnClickListener((View view) -> {
            if (holder.isFront) {
                holder.cardView.animate().rotationY(360f).setDuration(500);
                showRear(holder);
            } else {
                holder.cardView.animate().rotationY(360f).setDuration(500);
                showFront(holder);
            }
        });
    }
}
