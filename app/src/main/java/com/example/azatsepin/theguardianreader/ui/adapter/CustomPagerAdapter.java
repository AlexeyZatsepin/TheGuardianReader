package com.example.azatsepin.theguardianreader.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.azatsepin.theguardianreader.ui.fragments.SavedFragment;
import com.example.azatsepin.theguardianreader.ui.fragments.SearchFragment;

import java.util.ArrayList;
import java.util.List;

public class CustomPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments = new ArrayList<>();

    public CustomPagerAdapter(FragmentManager fm) {
        super(fm);
        addFragment(new SearchFragment());
        addFragment(new SavedFragment());
    }

    private void addFragment(Fragment fragment) {
        fragments.add(fragment);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
}
