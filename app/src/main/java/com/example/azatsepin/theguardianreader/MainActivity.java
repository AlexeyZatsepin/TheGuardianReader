package com.example.azatsepin.theguardianreader;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.azatsepin.theguardianreader.adapter.CustomPagerAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.view_pager);

        viewPager.setAdapter(new CustomPagerAdapter(getSupportFragmentManager()));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.action_bookmarks:
                            viewPager.setCurrentItem(0);
                            break;
                        case R.id.action_search:
                            viewPager.setCurrentItem(1);
                            break;
                    }
                    return true;
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private MenuItem prevMenuItem;
            @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null)
                    prevMenuItem.setChecked(false);
                else
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);

                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);
            }

            @Override public void onPageScrollStateChanged(int state) { }
        });
    }
}
