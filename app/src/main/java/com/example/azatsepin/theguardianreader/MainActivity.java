package com.example.azatsepin.theguardianreader;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.azatsepin.theguardianreader.adapter.CustomPagerAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = findViewById(R.id.view_pager);

        viewPager.setAdapter(new CustomPagerAdapter(getSupportFragmentManager()));

        viewPager.setCurrentItem(0);

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
    }
}
