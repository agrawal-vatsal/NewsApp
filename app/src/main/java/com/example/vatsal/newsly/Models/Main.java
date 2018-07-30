package com.example.vatsal.newsly.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Main {

    @SerializedName("articles")
    @Expose
    private List<Article> articles = null;

    public List<Article> getArticles() {
        return articles;
    }

}