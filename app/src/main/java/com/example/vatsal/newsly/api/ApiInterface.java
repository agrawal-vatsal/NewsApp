package com.example.vatsal.newsly.api;


import com.example.vatsal.newsly.Models.Main;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("everything")
    Call<Main> getTopHeadlines(@Query("apiKey") String apiKey,
                               @Query("language") String lan,
                               @Query("sources") String sources,
                               @Query("page") Integer page,
                               @Query("pageSize") Integer pageSize);

    @GET("everything")
    Call<Main> getTopHeadlines(@Query("apiKey") String apiKey,
                               @Query("language") String lan,
                               @Query("sortBy") String sortBy,
                               @Query("sources") String sources,
                               @Query("page") Integer page,
                               @Query("pageSize") Integer pageSize);

    @GET("everything")
    Call<Main> getTopHeadlines(@Query("apiKey") String apiKey,
                               @Query("language") String lan,
                               @Query("sortBy") String sortBy,
                               @Query("sources") String sources,
                               @Query("from") String fromDate,
                               @Query("to") String toDate,
                               @Query("page") Integer page,
                               @Query("pageSize") Integer pageSize);

    @GET("everything")
    Call<Main> getTopHeadlines(@Query("apiKey") String apiKey,
                               @Query("language") String lan,
                               @Query("sortBy") String sortBy,
                               @Query("from") String fromDate,
                               @Query("to") String toDate,
                               @Query("page") Integer page,
                               @Query("pageSize") Integer pageSize,
                               @Query("q") String query);

    @GET("top-headlines")
    Call<Main> getTopHeadlines(@Query("apiKey") String apiKey,
                               @Query("category") String category,
                               @Query("page") Integer page,
                               @Query("pageSize") Integer pageSize);

}
