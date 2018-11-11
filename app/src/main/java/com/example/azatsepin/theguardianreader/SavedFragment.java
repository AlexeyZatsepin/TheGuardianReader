package com.example.azatsepin.theguardianreader;

import android.arch.paging.DataSource;
import android.arch.paging.PagedList;
import android.arch.paging.PagedListAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.azatsepin.theguardianreader.adapter.ArticleAdapter;
import com.example.azatsepin.theguardianreader.model.Article;
import com.example.azatsepin.theguardianreader.utils.ArticleDiffUtilsCallback;

import java.util.concurrent.Executors;

public class SavedFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container,false);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);

//        PagedList.Config config = new PagedList.Config.Builder()
//                .setEnablePlaceholders(false)
//                .setPageSize(BuildConfig.DEFAULT_PAGE_SIZE)
//                .build();
//
//        DataSource.Factory<Integer, Article> dataSource = ((ReaderApp)getActivity().getApplication()).getArticleDao().getAllPaged();
//
//        PagedList<Article> pagedList = new PagedList.Builder<>(dataSource.create(), config)
//                .setFetchExecutor(Executors.newSingleThreadExecutor())
//                .setNotifyExecutor(command -> new Handler(Looper.getMainLooper()).post(command))
//                .build();
//
//        PagedListAdapter<Article, ArticleAdapter.ArticleViewHolder> adapter = new ArticleAdapter(new ArticleDiffUtilsCallback());
//        adapter.submitList(pagedList);
//
//        recyclerView.setAdapter(adapter);
        return root;
    }
}
