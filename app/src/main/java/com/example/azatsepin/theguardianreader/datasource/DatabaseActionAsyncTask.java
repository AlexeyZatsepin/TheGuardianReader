package com.example.azatsepin.theguardianreader.datasource;

import android.os.AsyncTask;

import com.example.azatsepin.theguardianreader.domain.Article;

public class DatabaseActionAsyncTask extends AsyncTask<Article, Void, Void> {

    public enum Action {DELETE, INSERT, UPDATE}

    public DatabaseActionAsyncTask(ArticleDao albumsDatabase, Action action) {
        mDao = albumsDatabase;
        mAction = action;
    }

    @Override
    protected Void doInBackground(final Article ... params) {

        switch (mAction){
            case DELETE:
                mDao.delete(params[0]);
                break;

            case INSERT:
                mDao.insert(params[0]);
                break;
            case UPDATE:
                mDao.update(params[0]);
                break;

            default:
                break;
        }
        return null;
    }

    private ArticleDao mDao;
    private Action mAction;
}