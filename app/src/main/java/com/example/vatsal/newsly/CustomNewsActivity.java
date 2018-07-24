package com.example.vatsal.newsly;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.example.vatsal.newsly.Adapters.UnsavedPostsRecyclerViewAdapter;
import com.example.vatsal.newsly.Models.Article;
import com.example.vatsal.newsly.Models.Main;
import com.example.vatsal.newsly.api.CustomCall;

import java.util.List;

import retrofit2.Response;

public class CustomNewsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    List<Article> dataset;
    public static int index = 1;
    UnsavedPostsRecyclerViewAdapter adapter;
    CustomCall initialCall;
    CustomCall laterCall;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_news);
        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progress_bar);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        initialCall = new CustomCall() {
            @Override
            public void onResponse(Response<Main> response) {
                if (response.body() != null) {
                    dataset = response.body().getArticles();
                    adapter = new UnsavedPostsRecyclerViewAdapter(dataset, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                    index = 2;
                    progressBar.setAlpha(0);
                }
            }
        };
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1))
                    onScrolledToBottom();
            }
        });
    }

    private void onScrolledToBottom() {
        laterCall = new CustomCall() {
            @Override
            public void onResponse(Response<Main> response) {
                if (response.body() != null) {
                    List<Article> list = response.body().getArticles();
                    dataset.addAll(list);
                    adapter.notifyDataSetChanged();
                }
            }
        };
    }
}
