package com.example.azatsepin.theguardianreader.datasource;

import android.arch.lifecycle.LiveData;

import com.example.azatsepin.theguardianreader.domain.Article;

import java.util.List;

public class AsyncArticleRepository implements CrudRepository<Article> {

    private ArticleDao dao;

    public AsyncArticleRepository(ArticleDao dao) {
        this.dao = dao;
    }

    public void create(Article article) {
        new DatabaseActionAsyncTask(dao, DatabaseActionAsyncTask.Action.INSERT)
                .execute(article);
    }

    @Override
    public LiveData<List<Article>> readAll() {
        return dao.getAll();
    }

    public LiveData<List<Article>> readAllPinned() {
        return dao.getPinnedArticles();
    }

    @Override
    public void update(Article article) {
        new DatabaseActionAsyncTask(dao, DatabaseActionAsyncTask.Action.UPDATE)
                .execute(article);
    }

    @Override
    public void delete(Article article) {
        new DatabaseActionAsyncTask(dao, DatabaseActionAsyncTask.Action.DELETE)
                .execute(article);
    }

}
