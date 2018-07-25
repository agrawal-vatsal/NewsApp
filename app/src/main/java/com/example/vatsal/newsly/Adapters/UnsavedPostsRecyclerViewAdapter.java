package com.example.vatsal.newsly.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.vatsal.newsly.Models.Article;
import com.example.vatsal.newsly.Models.ArticleDB;
import com.example.vatsal.newsly.Models.ArticleInterface;
import com.example.vatsal.newsly.WebPageActivity;

import java.util.ArrayList;
import java.util.List;


public class UnsavedPostsRecyclerViewAdapter extends RecyclerViewAdapter<Article> {

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        Article item = dataset.get(position);
        holder.cardView.setOnLongClickListener((View view) -> {
            boolean isInDb = checkItem(item.getTitle());
            String message = "Are you sure you want to " + (isInDb ? "delete" : "save") + " this post?";
            new AlertDialog.Builder(context)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Are you sure?")
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

    public UnsavedPostsRecyclerViewAdapter(List<Article> dataset, Context context) {
        this.dataset = dataset;
        this.context = context;
        setDb();
    }


}
