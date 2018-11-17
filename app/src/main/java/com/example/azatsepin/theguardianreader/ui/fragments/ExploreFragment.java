package com.example.azatsepin.theguardianreader.ui.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.azatsepin.theguardianreader.BuildConfig;
import com.example.azatsepin.theguardianreader.MainActivity;
import com.example.azatsepin.theguardianreader.R;
import com.example.azatsepin.theguardianreader.ui.adapter.ArticleAdapter;
import com.example.azatsepin.theguardianreader.ui.adapter.ArticlePagedAdapter;
import com.example.azatsepin.theguardianreader.ui.adapter.OnArticleClickListener;
import com.example.azatsepin.theguardianreader.ui.viewmodel.ArticlesViewModel;
import com.example.azatsepin.theguardianreader.utils.ArticleDiffUtilsCallback;

import java.util.ArrayList;

import static com.example.azatsepin.theguardianreader.MainActivity.openDetailsActivity;

public class ExploreFragment extends Fragment {

    private ArticleAdapter pinAdapter;
    private ArticlePagedAdapter adapter;
    private View divider;
    private RecyclerView recyclerViewPinned;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            adapter.notifyDataSetChanged();
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_explore, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        divider = root.findViewById(R.id.divider_pin);
        recyclerViewPinned = root.findViewById(R.id.recyclerViewPinned);
        LottieAnimationView progressBar = root.findViewById(R.id.animation_view);

        progressBar.setVisibility(View.VISIBLE);

        MainActivity activity = (MainActivity) getActivity();

        ArticlesViewModel model = ViewModelProviders.of(activity).get(ArticlesViewModel.class);

        OnArticleClickListener onArticleClickListener = (article, view) -> openDetailsActivity(getActivity(), view, article);

        model.getPinnedArticles().observe(activity, articles -> {
            if (pinAdapter == null && articles.size() > 0) {
                setPinnedBlockVisibility(View.VISIBLE);
                pinAdapter = new ArticleAdapter(articles);
                pinAdapter.addItemClickListener(onArticleClickListener);
                pinAdapter.useForPinned();
                recyclerViewPinned.setAdapter(pinAdapter);
            } else if (articles.size() > 0) {
                pinAdapter.update(articles);
            } else if (articles.size() == 0) {
                setPinnedBlockVisibility(View.GONE);
            }
        });

        adapter = new ArticlePagedAdapter(new ArticleDiffUtilsCallback());
        adapter.addItemClickListener(onArticleClickListener);
        model.getNetworkArticles().observe(activity, articles -> {
            articles.addWeakCallback(new ArrayList<>(), new PagedList.Callback() {
                @Override public void onChanged(int position, int count) { }
                @Override public void onInserted(int position, int count) {
                    progressBar.setVisibility(View.GONE);
                }
                @Override public void onRemoved(int position, int count) { }
            });
            adapter.submitList(articles);
        });
        recyclerView.setAdapter(adapter);

        activity.addListener(layoutManager -> {
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.swapAdapter(adapter, false);
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(receiver, new IntentFilter(BuildConfig.BROADCAST_ACTION));
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(receiver);
    }

    @Override
    public void setUserVisibleHint(boolean visible) {
        super.setUserVisibleHint(visible);
        if (visible && isResumed()) {
            MainActivity activity = (MainActivity) getActivity();
            activity.setSearchListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
//                ViewModelProviders.of(activity).get(ArticlesViewModel.class).getNetworkArticles(query);
                    Toast.makeText(getActivity(), "Search available only in saved tab", Toast.LENGTH_LONG).show();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
            adapter.notifyDataSetChanged();
        }
    }

    private void setPinnedBlockVisibility(int visibility) {
        recyclerViewPinned.setVisibility(visibility);
        divider.setVisibility(visibility);
    }
}
