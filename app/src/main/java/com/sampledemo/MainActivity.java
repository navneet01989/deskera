package com.sampledemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sampledemo.favorite.FavoriteFragment;
import com.sampledemo.items.ItemsFragment;
import com.sampledemo.profile.ProfileFragment;
import com.sampledemo.tables.TablesFragment;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private FrameLayout main_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottom_navigation = findViewById(R.id.bottom_navigation);
        main_content = findViewById(R.id.main_content);
        bottom_navigation.setOnNavigationItemSelectedListener(this);
        bottom_navigation.setSelectedItemId(R.id.action_profile);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_profile:
                getSupportFragmentManager().beginTransaction().replace(main_content.getId(), new ProfileFragment(), "ProfileFragment").commitAllowingStateLoss();
                break;
            case R.id.action_items:
                getSupportFragmentManager().beginTransaction().replace(main_content.getId(), new ItemsFragment(), "ItemsFragment").commitAllowingStateLoss();
                break;
            case R.id.action_favorites:
                getSupportFragmentManager().beginTransaction().replace(main_content.getId(), new FavoriteFragment(), "FavoriteFragment").commitAllowingStateLoss();
                break;
            case R.id.action_table:
                getSupportFragmentManager().beginTransaction().replace(main_content.getId(), new TablesFragment(), "TablesFragment").commitAllowingStateLoss();
                break;
            case R.id.action_setting:
                getSupportFragmentManager().beginTransaction().replace(main_content.getId(), new ProfileFragment(), "ProfileFragment").commitAllowingStateLoss();
                break;
        }
        return true;
    }
}
