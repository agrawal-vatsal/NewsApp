package com.example.vatsal.newsly.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class ArticleDB {

    @PrimaryKey
    @NonNull
    public String title;

    public String description;

    public String url;

    public String urlToImage;

    public ArticleDB(String title, String description, String url, String urlToImage) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
    }
}