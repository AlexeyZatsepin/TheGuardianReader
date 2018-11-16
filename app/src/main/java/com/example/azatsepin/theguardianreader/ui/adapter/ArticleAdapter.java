package com.example.azatsepin.theguardianreader.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import com.example.azatsepin.theguardianreader.domain.ArticleEntity;
import com.example.azatsepin.theguardianreader.utils.ArticleDiffUtilsCallback;
import com.example.azatsepin.theguardianreader.utils.ListArticleDiffUtilCallback;

import java.util.List;

public class ArticleAdapter extends ArticlePagedAdapter {

    private List<ArticleEntity> articleList;

    public ArticleAdapter(List<ArticleEntity> articleList) {
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

    public void update(List<ArticleEntity> articles) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new ListArticleDiffUtilCallback(articleList, articles));
        articleList.clear();
        articleList.addAll(articles);
//        diffResult.dispatchUpdatesTo(this);
        notifyDataSetChanged();
    }
}
