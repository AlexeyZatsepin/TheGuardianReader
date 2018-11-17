package com.example.azatsepin.theguardianreader.datasource;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.azatsepin.theguardianreader.domain.ArticleEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Articles is big object with large body string so it is bottle neck to pass it over parcel.
 */
public class InMemoryArticleRepository implements CrudRepository<ArticleEntity> {

    private Map<String, ArticleEntity> cache = new HashMap<>();

    @Override
    public void create(ArticleEntity article) {
        cache.put(article.getTitle(), article);
    }

    @Override
    public ArticleEntity read(String id) {
        return cache.get(id);
    }

    @Override
    public LiveData<List<ArticleEntity>> readAll() {
        MutableLiveData<List<ArticleEntity>> data = new MutableLiveData<>();
        data.postValue(new ArrayList<>(cache.values()));
        return data;
    }

    @Override
    public void update(ArticleEntity article) {
        cache.replace(article.getTitle(), article);
    }

    @Override
    public void delete(ArticleEntity article) {
        cache.remove(article.getTitle());
    }
}
