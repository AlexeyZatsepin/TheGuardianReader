package com.example.azatsepin.theguardianreader;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.azatsepin.theguardianreader.model.Article;
import com.example.azatsepin.theguardianreader.viewmodel.DetailsModelFactory;
import com.example.azatsepin.theguardianreader.viewmodel.DetailsViewModel;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class DetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        FloatingActionButton fab = findViewById(R.id.fab_share);
        LottieAnimationView progressBar = findViewById(R.id.animation_view);
        TextView pin = findViewById(R.id.pin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        ImageView imageView = findViewById(R.id.header_image);


        Article article = Objects.requireNonNull(getIntent().getExtras()).getParcelable("article");
        DetailsViewModel viewModel = ViewModelProviders.of(this,
                new DetailsModelFactory(article)).get(DetailsViewModel.class);
        viewModel.getArticle().observe(this, article1 -> {
            progressBar.setVisibility(View.VISIBLE);
            toolbar.setTitle(article1.getWebTitle());
            Picasso.get()
                    .load(article1.getFields().getThumbnail())
                    .into(imageView);
        });
    }
}
