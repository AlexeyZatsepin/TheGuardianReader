package com.example.azatsepin.theguardianreader.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.azatsepin.theguardianreader.R;
import com.example.azatsepin.theguardianreader.ReaderApp;
import com.example.azatsepin.theguardianreader.domain.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private List<Article> articleList;
    private OnArticleClickListener listener;

    public ArticleAdapter(List<Article> articleList) {
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        holder.bind(articleList.get(position));
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public void addItemClickListener(OnArticleClickListener listener) {
        this.listener = listener;
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder {
        private TextView titleView;
        private ImageView imageView;
        private ToggleButton button;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.primary_text);
            imageView = itemView.findViewById(R.id.media_image);
            button = itemView.findViewById(R.id.action_save);
        }

        public void bind(Article article){
            itemView.setOnClickListener(v -> listener.onClick(article, imageView));
            titleView.setText(article.getWebTitle());
            Picasso.get()
                    .load(article.getFields().getThumbnail())
                    .into(imageView);
            button.setOnClickListener(v -> ReaderApp.getInstance().getRepository().create(article));
        }
    }
}
