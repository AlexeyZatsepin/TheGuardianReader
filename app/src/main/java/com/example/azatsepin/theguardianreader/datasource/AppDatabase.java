package com.example.azatsepin.theguardianreader.datasource;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.azatsepin.theguardianreader.domain.Article;
import com.example.azatsepin.theguardianreader.domain.ArticleEntity;

@Database(entities = {ArticleEntity.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ArticleDao articleDao();
}
