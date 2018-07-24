package com.example.vatsal.newsly.DatabaseOperations;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.vatsal.newsly.Models.ArticleDB;


@Database(entities = {ArticleDB.class}, version = 1, exportSchema =  false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ArticleDao articleDao();
}
