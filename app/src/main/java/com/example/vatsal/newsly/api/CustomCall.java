package com.example.vatsal.newsly.api;


import com.example.vatsal.newsly.CustomNewsActivity;
import com.example.vatsal.newsly.CustomSearchFormActivity;
import com.example.vatsal.newsly.Fragments.NewsFragment;
import com.example.vatsal.newsly.Models.Main;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class CustomCall {

    protected CustomCall() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Main> call;
        if (CustomSearchFormActivity.query.equals(""))
            call = apiInterface.getTopHeadlines(NewsFragment.API_KEY,
                    CustomSearchFormActivity.language,
                    CustomSearchFormActivity.sortBy,
                    NewsFragment.sources,
                    CustomSearchFormActivity.fromDate,
                    CustomSearchFormActivity.toDate,
                    CustomNewsActivity.index++,
                    20);
        else
            call = apiInterface.getTopHeadlines(NewsFragment.API_KEY,
                    CustomSearchFormActivity.language,
                    CustomSearchFormActivity.sortBy,
                    CustomSearchFormActivity.fromDate,
                    CustomSearchFormActivity.toDate,
                    CustomNewsActivity.index++,
                    20,
                    CustomSearchFormActivity.query);

        call.enqueue(new Callback<Main>() {
            @Override
            public void onResponse(Call<Main> call, Response<Main> response) {
                CustomCall.this.onResponse(response);
            }

            @Override
            public void onFailure(Call<Main> call, Throwable t) {
            }
        });
    }

    public abstract void onResponse(Response<Main> response);
}
