package com.example.azatsepin.theguardianreader.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.azatsepin.theguardianreader.model.Article;

public class DetailsModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final Article article;

    public DetailsModelFactory(Article article) {
        super();
        this.article = article;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == DetailsViewModel.class) {
            return (T) new DetailsViewModel(article);
        }
        return null;
    }
}
