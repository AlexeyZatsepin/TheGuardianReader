package com.example.azatsepin.theguardianreader.ui.adapter;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.azatsepin.theguardianreader.R;
import com.example.azatsepin.theguardianreader.ReaderApp;
import com.example.azatsepin.theguardianreader.datasource.AsyncArticleRepository;
import com.example.azatsepin.theguardianreader.datasource.InMemoryArticleRepository;
import com.example.azatsepin.theguardianreader.domain.Article;
import com.example.azatsepin.theguardianreader.domain.ArticleEntity;
import com.example.azatsepin.theguardianreader.utils.RoundedCornersTransform;
import com.example.azatsepin.theguardianreader.utils.Utils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class ArticlePagedAdapter extends PagedListAdapter<Article, ArticlePagedAdapter.ArticleViewHolder> {

    private OnArticleClickListener listener;
    private ViewType viewType = ViewType.COMMON;
    private Transformation transformation = new RoundedCornersTransform(10,0);

    enum ViewType {
        PINNED, COMMON
    }

    public ArticlePagedAdapter(DiffUtil.ItemCallback<Article> diffUtilCallback) {
        super(diffUtilCallback);
    }

    @Override
    public int getItemViewType(int position) {
        return viewType.ordinal();
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewType type = ViewType.values()[viewType];
        if (type != ViewType.PINNED) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
            return new ArticleViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article_pinned, parent, false);
            return new PinnedArticleViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        ArticleEntity entity = ArticleEntity.fromArticle(getItem(position));
        checkInCacheAndBind(entity, holder);
    }

    public void addItemClickListener(OnArticleClickListener listener) {
        this.listener = listener;
    }

    public void useForPinned() {
        viewType = ViewType.PINNED;
    }

    protected void checkInCacheAndBind(ArticleEntity entity, ArticleViewHolder holder) {
        InMemoryArticleRepository cache = ReaderApp.getInstance().getCache();
        if (cache.read(entity.getTitle()) == null) cache.create(entity);
        else entity = cache.read(entity.getTitle());
        holder.bind(entity);
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder {
        private TextView titleView;
        private TextView secondaryText;
        protected ImageView imageView;
        protected ToggleButton button;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.primary_text);
            secondaryText = itemView.findViewById(R.id.sub_text);
            imageView = itemView.findViewById(R.id.media_image);
            button = itemView.findViewById(R.id.action_save);
        }

        public void bind(ArticleEntity article) {
            itemView.setOnClickListener(v -> listener.onClick(article, imageView));
            titleView.setText(article.getTitle());
            secondaryText.setText(Utils.formatDate(article.getDate()));
            Picasso.get()
                    .load(article.getThumbnail())
                    .placeholder(R.drawable.dr_drawer_background)
                    .fit()
                    .centerCrop()
                    .transform(transformation)
                    .into(imageView);
            button.setOnCheckedChangeListener(null);
            button.setChecked(article.isSaved());
            button.setOnCheckedChangeListener((buttonView, isChecked) -> {
                article.setSaved(isChecked);
                AsyncArticleRepository repository = ReaderApp.getInstance().getRepository();
                if (isChecked) {
                    repository.create(article);
                } else {
                    repository.delete(article);
                }
            });
        }
    }

    public class PinnedArticleViewHolder extends ArticleViewHolder {

        public PinnedArticleViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.media_image_pinned);
            button = itemView.findViewById(R.id.action_pin);
        }

        @Override
        public void bind(ArticleEntity article) {
            super.bind(article);
            button.setOnCheckedChangeListener(null);
            button.setChecked(article.isPinned());
            button.setOnCheckedChangeListener((buttonView, isChecked) -> {
                AsyncArticleRepository repository = ReaderApp.getInstance().getRepository();
                article.setPinned(!article.isPinned());
                if (article.isSaved() && article.isPinned()) {
                    repository.update(article);
                } else {
                    repository.delete(article);
                    article.setSaved(false);
                }
            });
        }
    }

}