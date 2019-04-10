package com.sampledemo.adapters;

import android.content.Context;
import android.os.Parcelable;

import com.sampledemo.R;
import com.sampledemo.item.ItemFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ItemsPagerAdapter extends FragmentStatePagerAdapter {
    private Context context;
    public ItemsPagerAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return ItemFragment.newInstance(position);
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
                return context.getString(R.string.all);
            case 1:
                return context.getString(R.string.category_a);
            case 2:
                return context.getString(R.string.category_b);
        }
        return super.getPageTitle(position);
    }

    @Nullable
    @Override
    public Parcelable saveState() {
        return null;
    }
}
