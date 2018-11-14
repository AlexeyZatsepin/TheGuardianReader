package com.example.azatsepin.theguardianreader.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.ListUpdateCallback;

import com.example.azatsepin.theguardianreader.domain.Article;
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
        articleList = articles;
        diffResult.dispatchUpdatesTo(new ListUpdateCallback() {
            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemRangeChanged(fromPosition, toPosition);
            }

            @Override
            public void onChanged(int position, int count, Object payload) {
                notifyItemRangeChanged(position,count, payload);
            }
        });
    }
}
