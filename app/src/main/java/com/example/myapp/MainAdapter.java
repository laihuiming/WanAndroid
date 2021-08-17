package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MainAdapter extends FragmentPagerAdapter {
    private String[] mTitles;
    private Fragment[] mfragment;
    public MainAdapter(@NonNull FragmentManager fm,String[] mTitles,Fragment[] mfragment) {
        super(fm);
        this.mTitles = mTitles;
        this.mfragment = mfragment;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mfragment[position];
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
