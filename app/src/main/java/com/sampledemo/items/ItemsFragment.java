package com.sampledemo.items;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.sampledemo.R;
import com.sampledemo.adapters.ItemsPagerAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class ItemsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.items_fragment, container, false);
        TabLayout tabLayout = rootView.findViewById(R.id.tabs);
        ViewPager viewPager = rootView.findViewById(R.id.viewpager);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(new ItemsPagerAdapter(getChildFragmentManager(), getContext()));
        return rootView;
    }
}
