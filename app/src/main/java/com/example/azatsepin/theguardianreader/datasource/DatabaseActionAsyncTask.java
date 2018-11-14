package com.example.azatsepin.theguardianreader.datasource;

import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;

import com.example.azatsepin.theguardianreader.domain.ArticleEntity;

public class DatabaseActionAsyncTask extends AsyncTask<ArticleEntity, Void, Void> {

    public enum Action {DELETE, INSERT, UPDATE}

    public DatabaseActionAsyncTask(ArticleDao albumsDatabase, Action action) {
        mDao = albumsDatabase;
        mAction = action;
    }

    @Override
    protected Void doInBackground(final ArticleEntity... params) {

        switch (mAction){
            case DELETE:
                mDao.delete(params[0]);
                break;
            case INSERT:
                try {
                    mDao.insert(params[0]);
                } catch (SQLiteConstraintException exception) {

                }
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