package com.example.azatsepin.theguardianreader;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import com.example.azatsepin.theguardianreader.utils.Utils;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    private Menu menu;
    private FloatingActionButton pinBtn;
    private DetailsViewModel viewModel;
    private Matrix matrix;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);



        pinBtn = findViewById(R.id.fab_pin);
        matrix = pinBtn.getImageMatrix();
//        LottieAnimationView progressBar = findViewById(R.id.animation_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout toolbarLayout = findViewById(R.id.collapsing_toolbar);

        ImageView imageView = findViewById(R.id.header_image);
        TextView headerView = findViewById(R.id.header);
        TextView pillarView = findViewById(R.id.pillar);
        TextView sectionView = findViewById(R.id.section);
        TextView dateView = findViewById(R.id.date);
        TextView bodyView = findViewById(R.id.body);
        TextView weblinkView = findViewById(R.id.weblink);

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
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            bundle.setClassLoader(ArticleEntity.class.getClassLoader());
            ArticleEntity article = bundle.getParcelable("article");
            viewModel = ViewModelProviders.of(this,
                    new DetailsModelFactory(article)).get(DetailsViewModel.class);
            viewModel.getArticle().observe(this, a -> {
                if (a==null) return;
                toolbarLayout.setTitle(a.getTitle());
                Picasso.get()
                        .load(a.getThumbnail())
                        .into(imageView);

                pinBtn.setOnClickListener(v -> onPinClick());

                headerView.setText(a.getTitle());
                pillarView.setText(a.getPillarName());
                sectionView.setText(a.getSectionName());
                dateView.setText(Utils.formatDate(a.getDate()));
                bodyView.setText(Html.fromHtml(a.getBody()));
                weblinkView.setOnClickListener(v -> {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(a.getLink()));
                    startActivity(browserIntent);
                });
//            progressBar.setVisibility(View.GONE);
            });
        } else {
            Snackbar.make(toolbarLayout, "Article not found", Snackbar.LENGTH_LONG).show();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_details, menu);
        hideOption(R.id.menu_pin);
        updateIconsStatus(viewModel.getArticle().getValue());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        ArticleEntity a = viewModel.getArticle().getValue();
        if (id == R.id.menu_save) {
            onSaveClick();
            return true;
        } else if (id == R.id.menu_pin) {
            onPinClick();
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

    private void onSaveClick(){
        AsyncArticleRepository repository = ((ReaderApp) getApplication()).getRepository();
        ArticleEntity a = viewModel.getArticle().getValue();
        a.setSaved(!a.isSaved());
        if (a.isSaved()) {
            repository.create(a);
        } else {
            repository.delete(a);
            a.setPinned(false);
        }
        updateIconsStatus(a);
        Snackbar.make(pinBtn, a.isSaved() ? "Saved" : "Removed", Snackbar.LENGTH_LONG).show();
    }

    private void onPinClick(){
        AsyncArticleRepository repository = ((ReaderApp) getApplication()).getRepository();
        ArticleEntity a = viewModel.getArticle().getValue();
        a.setPinned(!a.isPinned());

        if (a.isSaved() && a.isPinned()) {
            repository.update(a);
        } else if (!a.isSaved() && a.isPinned()){
            a.setSaved(true);
            repository.create(a);
        } else {
            a.setSaved(false);
            repository.delete(a);
        }

        updateIconsStatus(a);
        Snackbar.make(pinBtn, a.isPinned() ? "Pinned" : "Unpinned", Snackbar.LENGTH_LONG).show();
    }

    private void hideOption(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(false);
    }

    private void showOption(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(true);
    }

    private void updateIconsStatus(ArticleEntity a){
        pinBtn.setImageResource(a.isPinned() ? R.drawable.ic_pin : R.drawable.ic_pin_outline);
        pinBtn.setImageMatrix(matrix);
        menu.findItem(R.id.menu_save).setIcon(a.isSaved() ? R.drawable.ic_bookmark_check : R.drawable.ic_bookmark_plus_outline);
        menu.findItem(R.id.menu_pin).setIcon(a.isPinned() ? R.drawable.ic_pin : R.drawable.ic_pin_outline);
    }
}
