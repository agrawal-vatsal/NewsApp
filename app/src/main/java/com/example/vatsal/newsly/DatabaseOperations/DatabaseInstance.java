package com.example.vatsal.newsly.DatabaseOperations;

import android.arch.persistence.room.Room;
import android.content.Context;

public class DatabaseInstance {
    public static AppDatabase getInstance(Context context) {
        AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "articles")
                .allowMainThreadQueries()
                .build();
        return db;
    }
}
