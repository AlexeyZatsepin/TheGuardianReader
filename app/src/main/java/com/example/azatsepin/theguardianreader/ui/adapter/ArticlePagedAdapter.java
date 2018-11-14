package com.example.azatsepin.theguardianreader.ui.adapter;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.azatsepin.theguardianreader.R;
import com.example.azatsepin.theguardianreader.ReaderApp;
import com.example.azatsepin.theguardianreader.datasource.AsyncArticleRepository;
import com.example.azatsepin.theguardianreader.domain.Article;
import com.example.azatsepin.theguardianreader.domain.ArticleEntity;
import com.squareup.picasso.Picasso;

public class ArticlePagedAdapter extends PagedListAdapter<Article, ArticlePagedAdapter.ArticleViewHolder> {

    private OnArticleClickListener listener;

    public ArticlePagedAdapter(DiffUtil.ItemCallback<Article> diffUtilCallback) {
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
        holder.bind(ArticleEntity.fromArticle(getItem(position)));
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

        public void bind(ArticleEntity article){
            itemView.setOnClickListener(v -> listener.onClick(article, imageView));
            titleView.setText(article.getTitle());
            Picasso.get()
                    .load(article.getThumbnail())
                    .fit()
                    .centerCrop()
                    .into(imageView);
            button.setChecked(article.isPinned() || article.getId()!=0);
            button.setOnCheckedChangeListener((buttonView, isChecked) -> {
                AsyncArticleRepository repository = ReaderApp.getInstance().getRepository();
                if (isChecked) {
                    repository.create(article);
                } else {
                    repository.delete(article);
                }
            });
        }
    }

}