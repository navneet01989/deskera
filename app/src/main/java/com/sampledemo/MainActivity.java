package com.sampledemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sampledemo.favorite.FavoriteFragment;
import com.sampledemo.items.ItemsFragment;
import com.sampledemo.profile.ProfileFragment;
import com.sampledemo.settings.SettingsFragment;
import com.sampledemo.tables.TablesFragment;


public class MainActivity extends AppCompatActivity implements MainActivityPresenter.View {

    private FrameLayout main_content;
    private BottomNavigationView bottom_navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottom_navigation = findViewById(R.id.bottom_navigation);
        main_content = findViewById(R.id.main_content);
        bottom_navigation.setOnNavigationItemSelectedListener(this);
        bottom_navigation.setSelectedItemId(R.id.action_profile);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_profile:
                getSupportFragmentManager().beginTransaction().replace(main_content.getId(), ProfileFragment.newInstance(), "ProfileFragment").commitAllowingStateLoss();
                break;
            case R.id.action_items:
                getSupportFragmentManager().beginTransaction().replace(main_content.getId(), ItemsFragment.newInstance(), "ItemsFragment").commitAllowingStateLoss();
                break;
            case R.id.action_favorites:
                getSupportFragmentManager().beginTransaction().replace(main_content.getId(), FavoriteFragment.newInstance(), "FavoriteFragment").commitAllowingStateLoss();
                break;
            case R.id.action_table:
                getSupportFragmentManager().beginTransaction().replace(main_content.getId(), TablesFragment.newInstance(), "TablesFragment").commitAllowingStateLoss();
                break;
            case R.id.action_setting:
                getSupportFragmentManager().beginTransaction().replace(main_content.getId(), SettingsFragment.newInstance(), "SettingsFragment").commitAllowingStateLoss();
                break;
        }
        return true;
    }

    @Override
    public void toggleBottomView(boolean show) {
        if(show) {
            bottom_navigation.setVisibility(View.VISIBLE);
        } else {
            bottom_navigation.setVisibility(View.GONE);
        }
    }
}
