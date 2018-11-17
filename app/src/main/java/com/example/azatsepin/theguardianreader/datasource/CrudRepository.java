package com.example.azatsepin.theguardianreader.datasource;

import android.arch.lifecycle.LiveData;

import com.example.azatsepin.theguardianreader.domain.Article;

import java.util.List;

public interface CrudRepository<T> {
    void create(T item);

    T read(String id);

    LiveData<List<T>> readAll();

    void update(T item);

    void delete(T item);
}
