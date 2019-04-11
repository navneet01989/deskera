package com.sampledemo;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.fragment.app.Fragment;

public class MainActivityPresenter {
    private View view;
    MainActivityPresenter(View view) {
        this.view = view;
    }
    void setBottomNavigationVisibility(int visibility) {
        view.setBottomNavigationVisibility(visibility);
    }
    public interface View extends BottomNavigationView.OnNavigationItemSelectedListener {
        void toggleBottomView(boolean show);
        void setBottomNavigationVisibility(int visibility);
    }
}
