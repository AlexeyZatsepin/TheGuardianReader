package com.example.azatsepin.theguardianreader.ui.fragments;

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
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.azatsepin.theguardianreader.DetailsActivity;
import com.example.azatsepin.theguardianreader.R;
import com.example.azatsepin.theguardianreader.ui.adapter.ArticleAdapter;
import com.example.azatsepin.theguardianreader.ui.viewmodel.ArticlesViewModel;


public class SavedFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_saved, container,false);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        LottieAnimationView animationView = root.findViewById(R.id.animation_view);
        TextView textView = root.findViewById(R.id.empty_message);
        animationView.setVisibility(View.VISIBLE);

        ArticlesViewModel model = ViewModelProviders.of(getActivity()).get(ArticlesViewModel.class);

        model.getSavedArticles().observe(getActivity(), articles -> {
            if (articles.size()>0){
                textView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                ArticleAdapter adapter = new ArticleAdapter(articles);
                adapter.addItemClickListener((article, view) -> {
                    Intent intent = new Intent(getActivity(), DetailsActivity.class);
                    intent.putExtra("article", article);
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(getActivity(), view, getString(R.string.activity_transition_animation));
                    startActivity(intent, options.toBundle());
                });
                recyclerView.setAdapter(adapter);
            } else {
                recyclerView.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
            }
            animationView.setVisibility(View.GONE);
        });
        return root;
    }
}
