package com.sampledemo;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TempratureUnitActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temperature_unit_activity);
        TextView txtFarenheit = findViewById(R.id.txtFarenheit);
        TextView txtCelcius = findViewById(R.id.txtCelcius);
        txtFarenheit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferenceManager.getDefaultSharedPreferences(TempratureUnitActivity.this).edit().putString("TemperatureUnit", getString(R.string.farenheit)).apply();
                setResult(RESULT_OK);
                finish();
            }
        });
        txtCelcius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferenceManager.getDefaultSharedPreferences(TempratureUnitActivity.this).edit().putString("TemperatureUnit", getString(R.string.celsius)).apply();
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}
