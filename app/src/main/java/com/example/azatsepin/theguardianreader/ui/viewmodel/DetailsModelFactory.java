package com.example.azatsepin.theguardianreader.ui.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.azatsepin.theguardianreader.domain.Article;
import com.example.azatsepin.theguardianreader.domain.ArticleEntity;

public class DetailsModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final ArticleEntity article;

    public DetailsModelFactory(ArticleEntity article) {
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
