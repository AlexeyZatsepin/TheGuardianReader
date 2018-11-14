package com.example.azatsepin.theguardianreader.utils;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.example.azatsepin.theguardianreader.domain.ArticleEntity;

import java.util.List;

public class ListArticleDiffUtilCallback extends DiffUtil.Callback{
    List<ArticleEntity> oldArticles;
    List<ArticleEntity> newArticles;

    public ListArticleDiffUtilCallback(List<ArticleEntity> newPersons, List<ArticleEntity> oldPersons) {
        this.newArticles = newPersons;
        this.oldArticles = oldPersons;
    }

    @Override
    public int getOldListSize() {
        return oldArticles.size();
    }

    @Override
    public int getNewListSize() {
        return newArticles.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldArticles.get(oldItemPosition).getId() == newArticles.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldArticles.get(oldItemPosition).equals(newArticles.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        //you can return particular field for changed item.
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
