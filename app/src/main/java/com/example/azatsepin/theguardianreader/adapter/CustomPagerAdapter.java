package com.example.azatsepin.theguardianreader.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.azatsepin.theguardianreader.SavedFragment;
import com.example.azatsepin.theguardianreader.SearchFragment;

public class CustomPagerAdapter extends FragmentPagerAdapter {
    enum Pages {
        SEARCH, SAVED
    }

    public CustomPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return Pages.values().length;
    }

    @Override
    public Fragment getItem(int position) {
        return position == 0? new SearchFragment() : new SavedFragment();
    }
}
