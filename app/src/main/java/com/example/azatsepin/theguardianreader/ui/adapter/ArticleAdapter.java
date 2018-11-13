package com.example.azatsepin.theguardianreader.ui.adapter;

import android.support.annotation.NonNull;

import com.example.azatsepin.theguardianreader.domain.Article;
import com.example.azatsepin.theguardianreader.utils.ArticleDiffUtilsCallback;

import java.util.List;

public class ArticleAdapter extends ArticlePagedAdapter {

    private List<Article> articleList;

    public ArticleAdapter(List<Article> articleList) {
        super(new ArticleDiffUtilsCallback());
        this.articleList = articleList;
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        holder.bind(articleList.get(position));
    }
}
