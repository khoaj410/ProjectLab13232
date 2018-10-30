package com.example.khoaj.projectlab1.Fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainPagerAdapter extends FragmentPagerAdapter {


    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Fragment_Days();
            case 1:
                return new Fragment_Months();
            case 2:
                return new Fragment_Years();
            default:
                return new Fragment_Days();
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Days";
            case 1:
                return "Months";
            case 2:
                return "Years";
            default:
                return "Home";
        }
    }
}
