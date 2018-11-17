package com.example.azatsepin.theguardianreader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.azatsepin.theguardianreader.domain.ArticleEntity;
import com.example.azatsepin.theguardianreader.ui.adapter.CustomPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public interface LayoutManagerChangeListener {
        void onChange(RecyclerView.LayoutManager layoutManager);
    }

    private List<LayoutManagerChangeListener> listeners = new ArrayList<>();
    private SearchView.OnQueryTextListener searchListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.view_pager);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager.setAdapter(new CustomPagerAdapter(getSupportFragmentManager()));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.action_explore:
                            viewPager.setCurrentItem(0);
                            break;
                        case R.id.action_bookmarks:
                            viewPager.setCurrentItem(1);
                            break;
                    }
                    return true;
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private MenuItem prevMenuItem;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null)
                    prevMenuItem.setChecked(false);
                else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }

                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        ReaderApp.scheduleJob(getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem search = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setQueryHint("Enter Text");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
                return searchListener.onQueryTextSubmit(s);
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return searchListener.onQueryTextSubmit(s);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_change_layout:
                for (LayoutManagerChangeListener listener: listeners) {
                    listener.onChange(item.isChecked()? new LinearLayoutManager(getApplicationContext()) : new GridLayoutManager(getApplicationContext(),2));
                }
                item.setIcon(item.isChecked()? R.drawable.ic_format_list_bulleted : R.drawable.ic_grid_large);
                item.setChecked(!item.isChecked());
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    public void addListener(LayoutManagerChangeListener listener) {
        this.listeners.add(listener);
    }

    public void setSearchListener(SearchView.OnQueryTextListener listener) {
        searchListener = listener;
    }

    public static void openDetailsActivity(Activity activity, View transition, ArticleEntity entity) {
        Intent intent = new Intent(activity, DetailsActivity.class);
        intent.putExtra("article", entity.getTitle());
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(activity, transition, activity.getString(R.string.activity_transition_animation));
        activity.startActivity(intent, options.toBundle());
    }
}
