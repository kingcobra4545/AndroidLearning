package com.possystems.kingcobra.newsworld;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class TabsPagerAdapter extends FragmentPagerAdapter {
    String TAG = "TabsPagerAdapter";

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                Log.i(TAG, "CoverStoryFragment called");
                return  CoverStoryFragment.getInstance();
            case 1:
                Log.i(TAG, "TechnologyFragment called");
                return new TechnologyFragment();
            case 2:
                Log.i(TAG, "BusinessFragment called");
                return new BusinessFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }

}