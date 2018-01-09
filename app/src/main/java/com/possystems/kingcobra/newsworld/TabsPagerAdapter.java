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
                Log.d(TAG, "CoverStoryFragment called");
                return  CoverStoryFragment.getInstance();
            case 1:
                Log.d(TAG, "TechnologyFragment called");
                return  TechnologyFragment.getInstance();
            case 2:
                Log.d(TAG, "BusinessFragment called");
                return  BusinessFragment.getInstance();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }

}