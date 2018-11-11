package com.example.azatsepin.theguardianreader.datasource;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.azatsepin.theguardianreader.model.Article;

import java.util.List;


@Dao
public interface ArticleDao {

    @Query("SELECT * FROM Article")
    List<Article> getAll();

    @Query("SELECT * FROM Article")
    DataSource.Factory<Integer, Article> getAllPaged();

    @Query("SELECT * FROM Article WHERE id = :id")
    Article getById(long id);

    @Insert
    long insert(Article employee);

    @Update
    void update(Article employee);

    @Delete
    void delete(Article employee);

}
