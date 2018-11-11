package com.example.azatsepin.theguardianreader.adapter;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.azatsepin.theguardianreader.R;
import com.example.azatsepin.theguardianreader.model.Article;
import com.squareup.picasso.Picasso;

public class ArticleAdapter extends PagedListAdapter<Article, ArticleAdapter.ArticleViewHolder> {

    public ArticleAdapter(DiffUtil.ItemCallback<Article> diffUtilCallback) {
        super(diffUtilCallback);
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder {
        private TextView titleView;
        private ImageView imageView;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.primary_text);
            imageView = itemView.findViewById(R.id.media_image);
        }

        public void bind(Article article){
            titleView.setText(article.getWebTitle());
            Picasso.get()
                    .load(article.getFields().getThumbnail())
//                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(imageView);
        }
    }

}