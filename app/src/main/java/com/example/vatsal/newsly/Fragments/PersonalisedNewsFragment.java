package com.example.vatsal.newsly.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.vatsal.newsly.Adapters.PersonalisedNewsRecyclerViewAdapter;
import com.example.vatsal.newsly.Models.Article;
import com.example.vatsal.newsly.Models.Main;
import com.example.vatsal.newsly.Models.PersonalisedArticle;
import com.example.vatsal.newsly.R;
import com.example.vatsal.newsly.ThompsonSampling.ThompsonSampling;
import com.example.vatsal.newsly.api.ApiClient;
import com.example.vatsal.newsly.api.ApiInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalisedNewsFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ApiInterface apiService;
    ProgressBar progressBar;
    Map<String, Integer> categoriesMap;
    List<String> categories;
    ThompsonSampling sampling;
    public static final int pageSize = 20;
    List<PersonalisedArticle> dataset;
    PersonalisedNewsRecyclerViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_layout, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        progressBar = view.findViewById(R.id.progress_bar);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        progressBar.setAlpha(1f);
        categoriesMap = new HashMap<>();
        categories = Arrays.asList(getContext().getResources().getStringArray(R.array.categories));
        for (String category : categories)
            categoriesMap.put(category, 1);
        sampling = new ThompsonSampling(getContext());
        String bestCategory = sampling.getBest();
        Call<Main> call = apiService.getTopHeadlines(NewsFragment.API_KEY, bestCategory, categoriesMap.get(bestCategory), pageSize);
        sampling.addZeroes(bestCategory, pageSize);
        dataset = new ArrayList<>();
        call.enqueue(new Callback<Main>() {
            @Override
            public void onResponse(Call<Main> call, Response<Main> response) {
                if (response.body() != null) {
                    List<Article> articles = response.body().getArticles();
                    for (Article article : articles)
                        dataset.add(new PersonalisedArticle(article, bestCategory));
                    adapter = new PersonalisedNewsRecyclerViewAdapter(dataset, getContext(), sampling);
                    categoriesMap.put(bestCategory, categoriesMap.get(bestCategory) + 1);
                    recyclerView.setAdapter(adapter);
                    progressBar.setAlpha(0);
                }
            }

            @Override
            public void onFailure(Call<Main> call, Throwable t) {

            }
        });
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
        Call<Main> call;
        String bestCategory = sampling.getBest();
        call = apiService.getTopHeadlines(NewsFragment.API_KEY, bestCategory, categoriesMap.get(bestCategory), pageSize);
        sampling.addZeroes(bestCategory, pageSize);
        call.enqueue(new Callback<Main>() {
            @Override
            public void onResponse(Call<Main> call, Response<Main> response) {
                if (response.body() != null) {
                    List<Article> articles = response.body().getArticles();
                    for (Article article : articles)
                        dataset.add(new PersonalisedArticle(article, bestCategory));
                    categoriesMap.put(bestCategory, categoriesMap.get(bestCategory) + 1);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Main> call, Throwable t) {

            }
        });
    }
}
