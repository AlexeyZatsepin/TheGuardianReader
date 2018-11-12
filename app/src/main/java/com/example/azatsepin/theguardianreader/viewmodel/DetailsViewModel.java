package com.example.azatsepin.theguardianreader.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.azatsepin.theguardianreader.model.Article;

public class DetailsViewModel extends ViewModel {

    private MutableLiveData<Article> mutableLiveData;

    public DetailsViewModel(Article article) {
        mutableLiveData = new MutableLiveData<>();
        mutableLiveData.postValue(article);
    }

    public LiveData<Article> getArticle() {
        return mutableLiveData;
    }
}
