package com.example.vatsal.newsly.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class ArticleDB extends ArticleInterface {

    @PrimaryKey
    @NonNull
    public String title;

    public String description;

    public String url;

    public String urlToImage;

    public ArticleDB(@NonNull String title, String description, String url, String urlToImage) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
    }

    @NonNull
    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getUrlToImage() {
        return urlToImage;
    }
}