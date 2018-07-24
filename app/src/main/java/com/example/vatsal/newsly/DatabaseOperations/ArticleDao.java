package com.example.vatsal.newsly.DatabaseOperations;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.vatsal.newsly.Models.ArticleDB;

@Dao
public interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertArticle(ArticleDB article);

    @Delete
    void deleteArticle(ArticleDB article);

    @Query("SELECT * from ArticleDB")
    ArticleDB[] getArticles();

    @Query("SELECT * from ArticleDB where title = :title")
    ArticleDB[] getArticle(String title);
}
