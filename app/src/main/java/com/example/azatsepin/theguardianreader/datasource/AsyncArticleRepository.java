package com.example.azatsepin.theguardianreader.datasource;

import android.arch.lifecycle.LiveData;

import com.example.azatsepin.theguardianreader.domain.Article;
import com.example.azatsepin.theguardianreader.domain.ArticleEntity;

import java.util.List;

public class AsyncArticleRepository implements CrudRepository<ArticleEntity> {

    private ArticleDao dao;

    public AsyncArticleRepository(ArticleDao dao) {
        this.dao = dao;
    }

    public void create(ArticleEntity article) {
        new DatabaseActionAsyncTask(dao, DatabaseActionAsyncTask.Action.INSERT)
                .execute(article);
    }

    @Override
    public ArticleEntity read(String id) {
        return dao.getById(Long.valueOf(id)).getValue();
    }

    @Override
    public LiveData<List<ArticleEntity>> readAll() {
        return dao.getAll();
    }

    public LiveData<List<ArticleEntity>> readAllPinned() {
        return dao.getPinnedArticles();
    }

    @Override
    public void update(ArticleEntity article) {
        new DatabaseActionAsyncTask(dao, DatabaseActionAsyncTask.Action.UPDATE)
                .execute(article);
    }

    @Override
    public void delete(ArticleEntity article) {
        new DatabaseActionAsyncTask(dao, DatabaseActionAsyncTask.Action.DELETE)
                .execute(article);
    }

}
