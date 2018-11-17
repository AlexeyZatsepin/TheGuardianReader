package com.example.azatsepin.theguardianreader.utils;

import android.support.v7.util.DiffUtil;

import com.example.azatsepin.theguardianreader.domain.Article;

public final class ArticleDiffUtilsCallback extends DiffUtil.ItemCallback<Article> {

    @Override
    public boolean areItemsTheSame(Article oldItem, Article newItem) {
        return oldItem.getUrl().equals(newItem.getUrl());
    }

    @Override
    public boolean areContentsTheSame(Article oldItem, Article newItem) {
        return oldItem.getUrl().equals(newItem.getUrl())
                && oldItem.getFields().equals(newItem.getFields());
    }
}
