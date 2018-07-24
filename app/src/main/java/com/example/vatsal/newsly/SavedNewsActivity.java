package com.example.vatsal.newsly;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.vatsal.newsly.Adapters.SavedPostsRecyclerViewAdapter;

public class SavedNewsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    LinearLayoutManager layoutManager;
    SavedPostsRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_news);
        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setAlpha(1f);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SavedPostsRecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);
        progressBar.setAlpha(0);
    }
}
