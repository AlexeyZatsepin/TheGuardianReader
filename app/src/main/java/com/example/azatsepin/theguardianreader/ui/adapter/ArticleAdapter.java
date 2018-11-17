package com.example.azatsepin.theguardianreader.ui.adapter;

import android.support.annotation.NonNull;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.azatsepin.theguardianreader.domain.ArticleEntity;
import com.example.azatsepin.theguardianreader.utils.ArticleDiffUtilsCallback;

import java.util.ArrayList;
import java.util.List;

public class ArticleAdapter extends ArticlePagedAdapter implements Filterable {

    private List<ArticleEntity> articleList;
    private List<ArticleEntity> filteredList;

    public ArticleAdapter(List<ArticleEntity> articleList) {
        super(new ArticleDiffUtilsCallback());
        this.articleList = articleList;
        this.filteredList = articleList;
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        checkInCacheAndBind(filteredList.get(position), holder);
    }

    public void update(List<ArticleEntity> articles) {
        articleList.clear();
        articleList.addAll(articles);
        filteredList.clear();
        filteredList.addAll(articles);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String filterString = constraint.toString().toLowerCase();

                FilterResults results = new FilterResults();

                final List<ArticleEntity> nlist = new ArrayList<>(articleList.size());

                for (ArticleEntity filterableItem : articleList) {
                    if (filterableItem.getTitle().toLowerCase().contains(filterString)) {
                        nlist.add(filterableItem);
                    }
                }

                results.values = nlist;
                results.count = nlist.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                filteredList = (List<ArticleEntity>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
