package com.sampledemo;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivityPresenter {
    public interface View extends BottomNavigationView.OnNavigationItemSelectedListener {
        void toggleBottomView(boolean show);
    }
}
