package com.example.azatsepin.theguardianreader;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.azatsepin.theguardianreader.datasource.AsyncArticleRepository;
import com.example.azatsepin.theguardianreader.domain.Article;
import com.example.azatsepin.theguardianreader.ui.viewmodel.DetailsModelFactory;
import com.example.azatsepin.theguardianreader.ui.viewmodel.DetailsViewModel;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class DetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        FloatingActionButton fab = findViewById(R.id.fab_share);
//        LottieAnimationView progressBar = findViewById(R.id.animation_view);
        Button pin = findViewById(R.id.pin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView imageView = findViewById(R.id.header_image);
        TextView header = findViewById(R.id.header);
        TextView pillar = findViewById(R.id.pillar);
        TextView section = findViewById(R.id.section);
        TextView date = findViewById(R.id.date);
        TextView body = findViewById(R.id.body);
        TextView weblink = findViewById(R.id.weblink);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(view -> onBackPressed());

//        progressBar.setVisibility(View.VISIBLE);

        AsyncArticleRepository repository = ((ReaderApp) getApplication()).getRepository();

        Article article = Objects.requireNonNull(getIntent().getExtras()).getParcelable("article");
        DetailsViewModel viewModel = ViewModelProviders.of(this,
                new DetailsModelFactory(article)).get(DetailsViewModel.class);
        viewModel.getArticle().observe(this, a -> {
            toolbar.setTitle(a.getFields().getHeadline());
            Picasso.get()
                    .load(a.getFields().getThumbnail())
                    .into(imageView);

            fab.setOnClickListener(v -> {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, article.getFields().getHeadline());
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, article.getWebUrl());
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
            });

            pin.setOnClickListener(v -> {
                article.setPinned(!article.isPinned());
                if (article.isPinned()) {
                    repository.create(article);
                } else {
                    repository.update(article);
                }
            });
            header.setText(a.getWebTitle());
            pillar.setText(a.getPillarName());
            section.setText(a.getSectionName());
            date.setText(a.getWebPublicationDate());
            body.setText(Html.fromHtml(a.getFields().getBody()));
            weblink.setOnClickListener(v -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(a.getWebUrl()));
                startActivity(browserIntent);
            });

//            progressBar.setVisibility(View.GONE);
        });
    }
}
