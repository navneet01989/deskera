package com.sampledemo;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ViewDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_details_activity);
        TextView toolbar_back = findViewById(R.id.toolbar_back);
        TextView txtTemperatureUnit = findViewById(R.id.txtTemperatureUnit);
        TextView txtSound = findViewById(R.id.txtSound);
        TextView txtNotifications = findViewById(R.id.txtNotifications);
        txtTemperatureUnit.setText(PreferenceManager.getDefaultSharedPreferences(this).getString("TemperatureUnit", getString(R.string.farenheit)));
        txtSound.setText(String.valueOf(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("sound", true)));
        txtNotifications.setText(String.valueOf(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("notification", true)));
        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
