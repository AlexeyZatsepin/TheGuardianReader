package com.example.azatsepin.theguardianreader.ui.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.azatsepin.theguardianreader.MainActivity;
import com.example.azatsepin.theguardianreader.R;
import com.example.azatsepin.theguardianreader.ui.adapter.ArticleAdapter;
import com.example.azatsepin.theguardianreader.ui.viewmodel.ArticlesViewModel;

import static com.example.azatsepin.theguardianreader.MainActivity.openDetailsActivity;


public class SavedFragment extends Fragment {

    private ArticleAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_saved, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        LottieAnimationView animationView = root.findViewById(R.id.animation_view);
        TextView textView = root.findViewById(R.id.empty_message);
        animationView.setVisibility(View.VISIBLE);

        ArticlesViewModel model = ViewModelProviders.of(getActivity()).get(ArticlesViewModel.class);

        model.getSavedArticles().observe(getActivity(), articles -> {
            if (adapter == null && articles.size() > 0) {
                textView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                adapter = new ArticleAdapter(articles);
                adapter.addItemClickListener((article, view) -> openDetailsActivity(getActivity(), view, article));
                recyclerView.setAdapter(adapter);
            } else if (adapter != null && articles.size() > 0) {
                adapter.update(articles);
            } else {
                recyclerView.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
            }
            animationView.setVisibility(View.GONE);
        });

        ((MainActivity)getActivity()).addListener(layoutManager -> {
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.swapAdapter(adapter,false);
        });

        return root;
    }
}
