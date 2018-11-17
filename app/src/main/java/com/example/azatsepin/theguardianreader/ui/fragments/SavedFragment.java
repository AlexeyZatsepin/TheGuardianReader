package com.example.azatsepin.theguardianreader.ui.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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

        RecyclerView recyclerView = root.findViewById(R.id.recyclerViewSaved);
        LottieAnimationView animationView = root.findViewById(R.id.animation_view);
        TextView textView = root.findViewById(R.id.empty_message);
        animationView.setVisibility(View.VISIBLE);

        MainActivity activity = (MainActivity) getActivity();

        ArticlesViewModel model = ViewModelProviders.of(activity).get(ArticlesViewModel.class);

        model.getSavedArticles().observe(activity, articles -> {
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

        activity.addListener(layoutManager -> {
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.swapAdapter(adapter,false);
        });

        return root;
    }

    @Override
    public void setUserVisibleHint(boolean visible){
        super.setUserVisibleHint(visible);
        if (visible && isResumed()){
            ((MainActivity)getActivity()).setSearchListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    adapter.getFilter().filter(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
    }
}
