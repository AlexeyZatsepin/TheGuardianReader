package com.example.azatsepin.theguardianreader.ui.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.azatsepin.theguardianreader.BuildConfig;
import com.example.azatsepin.theguardianreader.MainActivity;
import com.example.azatsepin.theguardianreader.R;
import com.example.azatsepin.theguardianreader.ui.adapter.ArticleAdapter;
import com.example.azatsepin.theguardianreader.ui.adapter.ArticlePagedAdapter;
import com.example.azatsepin.theguardianreader.ui.adapter.OnArticleClickListener;
import com.example.azatsepin.theguardianreader.ui.viewmodel.ArticlesViewModel;
import com.example.azatsepin.theguardianreader.utils.ArticleDiffUtilsCallback;

import static com.example.azatsepin.theguardianreader.MainActivity.openDetailsActivity;

public class SearchFragment extends Fragment {
    private ArticleAdapter pinAdapter;
    private View divider;
    private RecyclerView recyclerViewPinned;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        divider = root.findViewById(R.id.divider_pin);
        recyclerViewPinned = root.findViewById(R.id.recyclerViewPinned);
        TextView textView = root.findViewById(R.id.empty_message);

        ArticlesViewModel model = ViewModelProviders.of(getActivity()).get(ArticlesViewModel.class);

        OnArticleClickListener onArticleClickListener = (article, view) -> openDetailsActivity(getActivity(), view, article);

        model.getPinnedArticles().observe(getActivity(), articles -> {
            if (pinAdapter == null && articles.size() > 0) {
                setPinnedBlockVisibility(View.VISIBLE);
                pinAdapter = new ArticleAdapter(articles);
                pinAdapter.addItemClickListener(onArticleClickListener);
                pinAdapter.useForPinned();
                recyclerViewPinned.setAdapter(pinAdapter);
            } else if (articles.size() > 0){
                pinAdapter.update(articles);
            } else if (articles.size() == 0) {
                setPinnedBlockVisibility(View.GONE);
            }
        });

        ArticlePagedAdapter adapter = new ArticlePagedAdapter(new ArticleDiffUtilsCallback());
        adapter.addItemClickListener(onArticleClickListener);
        //                textView.setVisibility(View.VISIBLE);
        model.getNetworkArticles().observe(getActivity(), adapter::submitList);
        recyclerView.setAdapter(adapter);

        ((MainActivity)getActivity()).addListener(layoutManager -> {
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.swapAdapter(adapter,false);
        });

//        ((MainActivity)getActivity()).setSearchListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });

        getActivity().registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                adapter.notifyDataSetChanged();
            }
        }, new IntentFilter(BuildConfig.BROADCAST_ACTION));

        return root;
    }

    private void setPinnedBlockVisibility(int visibility) {
        recyclerViewPinned.setVisibility(visibility);
        divider.setVisibility(visibility);
    }
}
