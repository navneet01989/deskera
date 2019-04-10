package com.sampledemo.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.sampledemo.R;
import com.sampledemo.TempratureUnitActivity;
import com.sampledemo.ViewDetailsActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {
    private final int REQUEST_CODE_TEMPERATURE = 121;
    private TextView txtTemperatureUnit;
    public static Fragment newInstance() {
        return new SettingsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View roorView = inflater.inflate(R.layout.settings_fragment, container, false);
        RelativeLayout rlvTemperature = roorView.findViewById(R.id.rlvTemperature);
        txtTemperatureUnit = roorView.findViewById(R.id.txtTemperatureUnit);
        txtTemperatureUnit.setText(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("TemperatureUnit", getString(R.string.farenheit)));
        rlvTemperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), TempratureUnitActivity.class), REQUEST_CODE_TEMPERATURE);
            }
        });
        Switch swtSound = roorView.findViewById(R.id.swtSound);
        swtSound.setChecked(PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("sound", true));
        swtSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putBoolean("sound", b).apply();
            }
        });
        Switch swtNotification = roorView.findViewById(R.id.swtNotification);
        swtNotification.setChecked(PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("notification", true));
        swtNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putBoolean("notification", b).apply();
            }
        });
        TextView txtViewDetails = roorView.findViewById(R.id.txtViewDetails);
        txtViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ViewDetailsActivity.class));
            }
        });
        return roorView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_TEMPERATURE) {
            txtTemperatureUnit.setText(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("TemperatureUnit", getString(R.string.farenheit)));
        }
    }
}
