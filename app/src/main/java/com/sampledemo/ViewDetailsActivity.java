package com.sampledemo;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ViewDetailsActivity extends AppCompatActivity {
    private final int SIX_MONTHS = 183;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_details_activity);
        TextView toolbar_back = findViewById(R.id.toolbar_back);
        TextView txtTemperatureUnit = findViewById(R.id.txtTemperatureUnit);
        TextView txtSound = findViewById(R.id.txtSound);
        TextView txtNotifications = findViewById(R.id.txtNotifications);
        TextView txtUsername = findViewById(R.id.txtUsername);
        TextView txtEmail = findViewById(R.id.txtEmail);
        TextView txtDoJ = findViewById(R.id.txtDoJ);
        TextView txtPEnds = findViewById(R.id.txtPEnds);
        TextView txtPDuration = findViewById(R.id.txtPDuration);
        TextView txtDateBecomePerm = findViewById(R.id.txtDateBecomePerm);

        txtTemperatureUnit.setText(PreferenceManager.getDefaultSharedPreferences(this).getString("TemperatureUnit", getString(R.string.farenheit)));
        txtSound.setText(String.valueOf(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("sound", true)));
        txtNotifications.setText(String.valueOf(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("notification", true)));
        String email = PreferenceManager.getDefaultSharedPreferences(this).getString("input_email", "");
        if(email.split("@").length > 0) {
            txtUsername.setText(email.split("@")[0]);
        }
        txtEmail.setText(email);
        String doj = PreferenceManager.getDefaultSharedPreferences(this).getString("input_do_joining", "");
        txtDoJ.setText(doj);
        try {
            if(!TextUtils.isEmpty(doj)) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                Date dateOfJoing = dateFormat.parse(doj);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateOfJoing);
                calendar.add(Calendar.DATE, SIX_MONTHS);
                Date dateOfPEnds = calendar.getTime();
                txtPEnds.setText(dateFormat.format(dateOfPEnds));
                calendar.add(Calendar.DATE, 1);
                txtDateBecomePerm.setText(dateFormat.format(calendar.getTime()));
                long diffInMillies = Math.abs(dateOfPEnds.getTime() - dateOfJoing.getTime());
                int diff = (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                int days = diff % 30;
                int months = diff / 30;
                txtPDuration.setText(getString(R.string.months_days, months, days));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
