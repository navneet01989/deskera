package com.sampledemo.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.sampledemo.R;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {
    private CircularImageView avatar;
    private int imgWidth = 0, imgHeight = 0;
    private EditText input_email, input_hobby, input_do_joining;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile_fragment, container, false);
        AppBarLayout appBarLayout = rootView.findViewById(R.id.appBarLayout);
        avatar = rootView.findViewById(R.id.avatar);
        input_email = rootView.findViewById(R.id.input_email);
        input_hobby = rootView.findViewById(R.id.input_hobby);
        input_do_joining = rootView.findViewById(R.id.input_do_joining);
        imgWidth = avatar.getLayoutParams().width;
        imgHeight = avatar.getLayoutParams().height;
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.BaseOnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                CollapsingToolbarLayout.LayoutParams params = new CollapsingToolbarLayout.LayoutParams(imgWidth - (int) (Math.abs(verticalOffset) * 0.5), imgHeight - (int) (Math.abs(verticalOffset) * 0.5));
                params.gravity = Gravity.END|Gravity.BOTTOM;
                avatar.setLayoutParams(params);
            }
        });
        input_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });
        input_hobby.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });
        input_do_joining.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });
        return rootView;
    }
}
