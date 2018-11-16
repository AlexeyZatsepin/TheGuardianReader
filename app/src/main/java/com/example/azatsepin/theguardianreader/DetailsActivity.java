package com.example.azatsepin.theguardianreader;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.azatsepin.theguardianreader.datasource.AsyncArticleRepository;
import com.example.azatsepin.theguardianreader.domain.ArticleEntity;
import com.example.azatsepin.theguardianreader.ui.viewmodel.DetailsModelFactory;
import com.example.azatsepin.theguardianreader.ui.viewmodel.DetailsViewModel;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class DetailsActivity extends AppCompatActivity {

    private Menu menu;
    private FloatingActionButton pinBtn;
    private DetailsViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        pinBtn = findViewById(R.id.fab_pin);
//        LottieAnimationView progressBar = findViewById(R.id.animation_view);
//        ToggleButton pin = findViewById(R.id.pin);
//        ToggleButton save = findViewById(R.id.action_save);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView imageView = findViewById(R.id.header_image);
        TextView header = findViewById(R.id.header);
        TextView pillar = findViewById(R.id.pillar);
        TextView section = findViewById(R.id.section);
        TextView date = findViewById(R.id.date);
        TextView body = findViewById(R.id.body);
        TextView weblink = findViewById(R.id.weblink);
        AppBarLayout appBarLayout = findViewById(R.id.app_bar_layout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;
                    showOption(R.id.menu_pin);
                } else if (isShow) {
                    isShow = false;
                    hideOption(R.id.menu_pin);
                }
            }
        });


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(view -> onBackPressed());

//        progressBar.setVisibility(View.VISIBLE);

        ArticleEntity article = Objects.requireNonNull(getIntent().getExtras()).getParcelable("article");
        viewModel = ViewModelProviders.of(this,
                new DetailsModelFactory(article)).get(DetailsViewModel.class);
        viewModel.getArticle().observe(this, a -> {
            if (a==null) return;
            toolbar.setTitle(a.getTitle());
            Picasso.get()
                    .load(a.getThumbnail())
                    .into(imageView);

            pinBtn.setOnClickListener(v -> {
                a.setPinned(!a.isPinned());
                onSaveClick(a.isPinned());
                setImagePin(a.isPinned());
            });

            setImagePin(a.isPinned());

            header.setText(a.getTitle());
            pillar.setText(a.getPillarName());
            section.setText(a.getSectionName());
            date.setText(a.getDate());
            body.setText(Html.fromHtml(a.getBody()));
            weblink.setOnClickListener(v -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(a.getLink()));
                startActivity(browserIntent);
            });

//            progressBar.setVisibility(View.GONE);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_details, menu);
        hideOption(R.id.menu_pin);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        viewModel.getArticle().observe(this, articleEntity -> {
            menu.findItem(R.id.menu_save).setIcon(articleEntity.getId()!=0? R.drawable.ic_bookmark_check : R.drawable.ic_bookmark_plus_outline);
            menu.findItem(R.id.menu_pin).setIcon(articleEntity.isPinned()? R.drawable.ic_pin : R.drawable.ic_pin_outline);
        });
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        ArticleEntity a = viewModel.getArticle().getValue();
        if (id == R.id.menu_save) {
            onSaveClick(item.isChecked());
            return true;
        } else if (id == R.id.menu_pin) {
            a.setPinned(!a.isPinned());
            onSaveClick(a.isPinned());
            return true;
        } else if (id == R.id.menu_share) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, a.getTitle());
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, a.getLink());
            startActivity(Intent.createChooser(sharingIntent, "Share using"));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onSaveClick(boolean isChecked){
        AsyncArticleRepository repository = ((ReaderApp) getApplication()).getRepository();
        ArticleEntity a = viewModel.getArticle().getValue();
        if (isChecked) {
            repository.create(a);
        } else if(a.getId()!=0) {
            repository.update(a);
        } else {
            repository.delete(a);
        }
    }

    private void setImagePin(boolean checked){
        if (checked) {
            pinBtn.setImageResource(R.drawable.ic_pin);
        } else {
            pinBtn.setImageResource(R.drawable.ic_pin_outline);
        }
    }

    private void hideOption(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(false);
    }

    private void showOption(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(true);
    }
}
