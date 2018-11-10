package com.example.azatsepin.theguardianreader;

import android.arch.paging.PagedList;
import android.arch.paging.PagedListAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.azatsepin.theguardianreader.model.Article;
import com.example.azatsepin.theguardianreader.persistent.NetworkDataSource;
import com.example.azatsepin.theguardianreader.utils.ArticleDiffUtilsCallback;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(BuildConfig.DEFAULT_PAGE_SIZE)
                .build();

        NetworkDataSource networkDataSource = new NetworkDataSource(((ReaderApp)getApplication()).getGuardianApi());

        PagedList<Article> pagedList = new PagedList.Builder<>(networkDataSource, config)
                .setFetchExecutor(Executors.newSingleThreadExecutor())
                .setNotifyExecutor(command -> new Handler(Looper.getMainLooper()).post(command))
                .build();

        PagedListAdapter<Article, ArticleAdapter.ArticleViewHolder> adapter = new ArticleAdapter(new ArticleDiffUtilsCallback());
        adapter.submitList(pagedList);

        recyclerView.setAdapter(adapter);
    }
}
