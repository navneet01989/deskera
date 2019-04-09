package com.sampledemo.profile;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.sampledemo.BuildConfig;
import com.sampledemo.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment implements ProfilePresenter.View {
    private final int PERMISSION_REQUEST_CODE_CAMERA = 303, CAMERA_REQUEST_CODE = 403;
    private Context context;
    private String cameraFilePath;
    private ProfilePresenter mProfilePresenter;
    private EditText input_email;
    private EditText input_hobby;
    private EditText input_do_joining;
    private CircularImageView avatar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile_fragment, container, false);
        AppBarLayout appBarLayout = rootView.findViewById(R.id.appBarLayout);
        input_email = rootView.findViewById(R.id.input_email);
        input_hobby = rootView.findViewById(R.id.input_hobby);
        input_do_joining = rootView.findViewById(R.id.input_do_joining);
        avatar = rootView.findViewById(R.id.avatar);
        final int imgWidth = avatar.getLayoutParams().width;
        final int imgHeight = avatar.getLayoutParams().height;
        mProfilePresenter = new ProfilePresenter(this);
        mProfilePresenter.setTextForEmail();
        mProfilePresenter.setTextForHobby();
        mProfilePresenter.setTextForJoin();
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
                mProfilePresenter.updateKeyValueForEmail(charSequence.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });
        input_hobby.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mProfilePresenter.updateKeyValueForHobby(charSequence.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });
        input_do_joining.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mProfilePresenter.updateKeyValueForJoin(charSequence.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProfilePresenter.showChoiceDialog();
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void updateValueInSharedPreferenceForEmail(String value) {
        if(context != null) {
            PreferenceManager.getDefaultSharedPreferences(context).edit().putString("input_email", value).apply();
        }
    }

    @Override
    public void updateValueInSharedPreferenceForHobby(String value) {
        if(context != null) {
            PreferenceManager.getDefaultSharedPreferences(context).edit().putString("input_hobby", value).apply();
        }
    }

    @Override
    public void updateValueInSharedPreferenceForJoin(String value) {
        if(context != null) {
            PreferenceManager.getDefaultSharedPreferences(context).edit().putString("input_do_joining", value).apply();
        }
    }

    @Override
    public void setTextForEmail() {
        if(context != null) {
            input_email.setText(PreferenceManager.getDefaultSharedPreferences(context).getString("input_email", ""));
        }
    }

    @Override
    public void setTextForHobby() {
        if(context != null) {
            input_hobby.setText(PreferenceManager.getDefaultSharedPreferences(context).getString("input_hobby", ""));
        }
    }

    @Override
    public void setTextForJoin() {
        if(context != null) {
            input_do_joining.setText(PreferenceManager.getDefaultSharedPreferences(context).getString("input_do_joining", ""));
        }
    }

    @Override
    public void showChoiceDialog() {
        if(context != null) {
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);
            builderSingle.setTitle(getString(R.string.please_choose));
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1);
            arrayAdapter.add(getString(R.string.camera));
            arrayAdapter.add(getString(R.string.gallery));
            builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            mProfilePresenter.cameraPermissionAsk();
                            break;
                        case 1:
                            break;
                    }
                }
            });
            builderSingle.setCancelable(true);
            builderSingle.show();
        }
    }

    @Override
    public void cameraPermissionAsk() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                mProfilePresenter.captureFromCamera();
            } else if ((ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                    ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
                    && getActivity() != null) {
                if(!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) || !shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    mProfilePresenter.showRationale(getString(R.string.camera_msg));
                    return;
                }
                ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_CAMERA);
            }
        } else {
            mProfilePresenter.captureFromCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_REQUEST_CODE_CAMERA) {
            if((grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) ||
                    (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                mProfilePresenter.captureFromCamera();
            }
        }
    }

    @Override
    public void captureFromCamera() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", createImageFile()));
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void setAvatarImage() {
        avatar.setImageURI(Uri.parse(cameraFilePath));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && CAMERA_REQUEST_CODE == requestCode) {
            mProfilePresenter.setAvatarImage();
        }
    }

    @Override
    public void showRationale(String msg) {
        AlertDialog.Builder builderAlert = new AlertDialog.Builder(context);
        builderAlert.setTitle(msg);
        builderAlert.setPositiveButton(getString(R.string.allow), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(getActivity() != null) {
                    if((!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) && !shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) 
                            && PreferenceManager.getDefaultSharedPreferences(context).getBoolean("isCameraFirstTime", true) 
                            && PreferenceManager.getDefaultSharedPreferences(context).getBoolean("isStorageFirstTime", true)) {
                        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean("isCameraFirstTime", false).apply();
                        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean("isStorageFirstTime", false).apply();
                        ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_CAMERA);
                    } else if(!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)
                            && PreferenceManager.getDefaultSharedPreferences(context).getBoolean("isCameraFirstTime", true)) {
                        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean("isCameraFirstTime", false).apply();
                        ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE_CAMERA);
                    } else if(!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            && PreferenceManager.getDefaultSharedPreferences(context).getBoolean("isStorageFirstTime", true)) {
                        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean("isStorageFirstTime", false).apply();
                        ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_CAMERA);
                    } else {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                        intent.setData(uri);
                        context.startActivity(intent);
                    }
                }

            }
        });
        builderAlert.setNegativeButton(getString(R.string.deny), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builderAlert.setCancelable(true);
        builderAlert.show();
    }
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(imageFileName,".jpg", storageDir);
        cameraFilePath = "file://" + image.getAbsolutePath();
        return image;
    }
}
