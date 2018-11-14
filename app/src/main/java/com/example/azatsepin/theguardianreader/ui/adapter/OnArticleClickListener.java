package com.example.azatsepin.theguardianreader.ui.adapter;

import android.view.View;

import com.example.azatsepin.theguardianreader.domain.Article;
import com.example.azatsepin.theguardianreader.domain.ArticleEntity;

public interface OnArticleClickListener {
    void onClick(ArticleEntity article, View view);
}
