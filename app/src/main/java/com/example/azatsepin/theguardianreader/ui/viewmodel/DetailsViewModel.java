package com.example.azatsepin.theguardianreader.ui.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.azatsepin.theguardianreader.domain.Article;
import com.example.azatsepin.theguardianreader.domain.ArticleEntity;

public class DetailsViewModel extends ViewModel {

    private MutableLiveData<ArticleEntity> mutableLiveData;

    public DetailsViewModel(ArticleEntity article) {
        mutableLiveData = new MutableLiveData<>();
        mutableLiveData.postValue(article);
    }

    public LiveData<ArticleEntity> getArticle() {
        return mutableLiveData;
    }
}
