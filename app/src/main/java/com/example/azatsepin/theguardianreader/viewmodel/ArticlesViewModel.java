package com.example.azatsepin.theguardianreader.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PagedList;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.example.azatsepin.theguardianreader.BuildConfig;
import com.example.azatsepin.theguardianreader.ReaderApp;
import com.example.azatsepin.theguardianreader.datasource.api.GuardianApi;
import com.example.azatsepin.theguardianreader.datasource.ArticleDao;
import com.example.azatsepin.theguardianreader.datasource.api.NetworkDataSource;
import com.example.azatsepin.theguardianreader.model.Article;

import java.util.List;
import java.util.concurrent.Executors;

public class ArticlesViewModel extends AndroidViewModel {

    private MutableLiveData<PagedList<Article>> networkArticles;
    private MutableLiveData<List<Article>> pinnedArticles;
    private MutableLiveData<List<Article>> savedArticles;
    private GuardianApi api;
    private ArticleDao articleDao;

    public ArticlesViewModel(@NonNull Application application) {
        super(application);
        ReaderApp app = (ReaderApp) application;
        api = app.getGuardianApi();
        articleDao = app.getArticleDao();
    }

    public LiveData<PagedList<Article>> getNetworkArticles() {
        if (networkArticles == null) {
            networkArticles = new MutableLiveData<>();
            PagedList.Config config = new PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setPageSize(BuildConfig.DEFAULT_PAGE_SIZE)
                    .build();

            NetworkDataSource networkDataSource = new NetworkDataSource(api);

            PagedList<Article> pagedList = new PagedList.Builder<>(networkDataSource, config)
                    .setFetchExecutor(Executors.newSingleThreadExecutor())
                    .setNotifyExecutor(command -> new Handler(Looper.getMainLooper()).post(command))
                    .build();
            networkArticles.postValue(pagedList);
        }
        return networkArticles;
    }

    public LiveData<List<Article>> getPinnedArticles() {
        if (pinnedArticles == null) {
            pinnedArticles = new MutableLiveData<>();
            articleDao.getPinnedArticles().observe(getApplication(), pinnedArticles::postValue);
        }
        return pinnedArticles;
    }

    public LiveData<List<Article>> getSavedArticles() {
        if (savedArticles == null) {
            savedArticles = new MutableLiveData<>();
            articleDao.getPinnedArticles().observe(getApplication(), savedArticles::postValue);
        }
        return savedArticles;
    }
}
