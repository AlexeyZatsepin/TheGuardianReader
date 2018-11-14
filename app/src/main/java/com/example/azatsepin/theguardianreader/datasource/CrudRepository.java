package com.example.azatsepin.theguardianreader.datasource;

import android.arch.lifecycle.LiveData;

import com.example.azatsepin.theguardianreader.domain.Article;

import java.util.List;

public interface CrudRepository<T> {
    void create(T article);

    LiveData<List<T>> readAll();

    void update(T article);

    void delete(T article);
}
