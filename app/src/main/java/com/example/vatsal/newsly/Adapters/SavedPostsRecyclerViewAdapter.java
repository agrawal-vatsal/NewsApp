package com.example.vatsal.newsly.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.example.vatsal.newsly.Models.ArticleDB;

import java.util.ArrayList;
import java.util.Arrays;

public class SavedPostsRecyclerViewAdapter extends RecyclerViewAdapter<ArticleDB> {

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.cardView.setOnLongClickListener((View view) -> {
            new AlertDialog.Builder(context)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Are you sure?")
                    .setMessage("Are you sure you want to delete this post?")
                    .setNegativeButton("No", null)
                    .setPositiveButton("Yes", (DialogInterface dialogInterface, int i) -> {
                        dataset.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, dataset.size());
                    })
                    .show();
            return true;
        });
    }

    public SavedPostsRecyclerViewAdapter(Context context) {
        this.context = context;
        setDb();
        dataset = new ArrayList<>(Arrays.asList(db.articleDao().getArticles()));
    }
}
