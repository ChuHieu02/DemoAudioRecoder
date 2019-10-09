package com.hieu.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.hieu.fragment.FragmentDetailInformation;
import com.hieu.fragment.FragmentDetailListAudio;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new FragmentDetailInformation();
            case 1:
                return new FragmentDetailListAudio();

            default:
                return null;
        }

    }



    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}