package com.sampledemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sampledemo.models.Table;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TablesDetailsActivity extends AppCompatActivity {
    private boolean isEditing = false;
    private EditText edtItem_name;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_details_activity);
        edtItem_name = findViewById(R.id.edtItem_name);
        Button btnBack = findViewById(R.id.btnBack);
        final Button btnEditSave = findViewById(R.id.btnEditSave);
        Table table = (Table) getIntent().getSerializableExtra("data");
        edtItem_name.setText(table.getName());
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customFinish();
            }
        });
        btnEditSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isEditing) {
                    isEditing = true;
                    edtItem_name.setEnabled(true);
                    btnEditSave.setText(getString(R.string.save));
                    edtItem_name.requestFocus();
                } else {
                    isEditing = false;
                    edtItem_name.setEnabled(false);
                    btnEditSave.setText(getString(R.string.edit));
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        customFinish();
    }

    private void customFinish() {
        Intent intent = new Intent();
        int position = getIntent().getIntExtra("position", -1);
        intent.putExtra("position", position);
        intent.putExtra("data", edtItem_name.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}
