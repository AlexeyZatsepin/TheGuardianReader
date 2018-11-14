package com.example.azatsepin.theguardianreader.datasource;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.azatsepin.theguardianreader.domain.Article;
import com.example.azatsepin.theguardianreader.domain.ArticleEntity;

import java.util.List;


@Dao
public interface ArticleDao {

    @Query("SELECT * FROM Article")
    LiveData<List<ArticleEntity>> getAll();

    @Query("SELECT * FROM Article WHERE pinned == 1")
    LiveData<List<ArticleEntity>> getPinnedArticles();

    @Query("SELECT * FROM Article")
    DataSource.Factory<Integer, ArticleEntity> getAllPaged();

    @Query("SELECT * FROM Article WHERE id = :id")
    LiveData<ArticleEntity> getById(long id);

    @Insert
    long insert(ArticleEntity articleEntity);

    @Update
    void update(ArticleEntity articleEntity);

    @Delete
    void delete(ArticleEntity articleEntity);

}
