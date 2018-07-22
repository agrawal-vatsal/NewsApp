package com.example.vatsal.newsly.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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
import com.example.vatsal.newsly.Models.Article;
import com.example.vatsal.newsly.Models.ArticleDB;
import com.example.vatsal.newsly.R;
import com.example.vatsal.newsly.WebPageActivity;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Article> dataset;
    private Context context;
    private AppDatabase db;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.recycler_view_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Article item = dataset.get(position);
        holder.textView.setText(item.getTitle());
        Glide.with(context)
                .load(item.getUrlToImage())
                .into(holder.imageView);
        showFront(holder);
        holder.description.setText(item.getDescription());
        holder.readFull.setOnClickListener((View view) -> {
            Intent intent = new Intent(context, WebPageActivity.class);
            intent.putExtra("webPage", item.getUrl());
            context.startActivity(intent);

        });

        holder.cardView.setOnClickListener((View view) -> {
            if (holder.isFront) {
                holder.cardView.animate().rotationY(360f).setDuration(500);
                showRear(holder);
            } else {
                holder.cardView.animate().rotationY(360f).setDuration(500);
                showFront(holder);
            }
        });
        holder.cardView.setOnLongClickListener((View view) -> {
            boolean isInDb = checkItem(item.getTitle());
            String message = "Are you sure you want to " + (isInDb ? "delete" : "save") + " this post?";
            new AlertDialog.Builder(context)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Are you sure")
                    .setMessage(message)
                    .setPositiveButton("Yes", (DialogInterface dialogInterface, int i) -> {
                        ArticleDB article = new ArticleDB(item.getTitle(), item.getDescription(), item.getUrl(), item.getUrlToImage());
                        if (isInDb)
                            db.articleDao().deleteArticle(article);
                        else
                            db.articleDao().insertArticle(article);
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        });

    }

    private boolean checkItem(String title) {
        return db.articleDao().getArticle(title).length != 0;
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

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

    public RecyclerViewAdapter(List<Article> dataset, Context context) {
        this.dataset = dataset;
        this.context = context;
        this.db = DatabaseInstance.getInstance(context);
    }

    private void showRear(ViewHolder holder) {
        holder.isFront = false;
        holder.textView.setAlpha(0f);
        holder.imageView.setAlpha(0f);
        holder.description.setAlpha(1f);
        holder.readFull.setAlpha(1f);
        holder.readFull.setClickable(true);
    }

    private void showFront(ViewHolder holder) {
        holder.isFront = true;
        holder.textView.setAlpha(1f);
        holder.imageView.setAlpha(1f);
        holder.description.setAlpha(0f);
        holder.readFull.setAlpha(0f);
        holder.readFull.setClickable(false);
    }
}
