package com.example.azatsepin.theguardianreader;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.azatsepin.theguardianreader.adapter.ArticlePagedAdapter;
import com.example.azatsepin.theguardianreader.utils.ArticleDiffUtilsCallback;
import com.example.azatsepin.theguardianreader.viewmodel.ArticlesViewModel;

public class SearchFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);

        ArticlesViewModel model = ViewModelProviders.of(getActivity()).get(ArticlesViewModel.class);
        ArticlePagedAdapter adapter = new ArticlePagedAdapter(new ArticleDiffUtilsCallback());
        adapter.addItemClickListener((article, view) -> {
            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            intent.putExtra("article", article);
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(getActivity(), view, getString(R.string.activity_transition_animation));
            startActivity(intent, options.toBundle());
        });
        model.getNetworkArticles().observe(getActivity(), adapter::submitList);
        recyclerView.setAdapter(adapter);

        return root;
    }
}
