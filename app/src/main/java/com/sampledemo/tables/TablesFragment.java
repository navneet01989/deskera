package com.sampledemo.tables;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sampledemo.MainActivityPresenter;
import com.sampledemo.R;
import com.sampledemo.TablesDetailsActivity;
import com.sampledemo.adapters.TablesRecyclerAdapter;
import com.sampledemo.models.Table;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TablesFragment extends Fragment implements TablesPresenter.View {
    private boolean isEditing = false;
    private int selectedItemCount = 0;
    private TextView toolbar_title;
    private Button btnDelete;
    private MainActivityPresenter.View listener;
    private final int REQUEST_CODE_EDIT = 232;
    public static Fragment newInstance() {
        return new TablesFragment();
    }
    private List<Table> list;
    private TablesRecyclerAdapter tablesRecyclerAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tables_fragment, container, false);
        final ImageView imgAdd = rootView.findViewById(R.id.imgAdd);
        final TextView toolbar_edit = rootView.findViewById(R.id.toolbar_edit);
        toolbar_title = rootView.findViewById(R.id.toolbar_title);
        btnDelete = rootView.findViewById(R.id.btnDelete);
        final EditText edtSearch = rootView.findViewById(R.id.edtSearch);
        final RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        Type listType = new TypeToken<List<Table>>() {}.getType();
        list = new Gson().fromJson(sampleJson, listType);
        Collections.sort(list, mapComparator);
        tablesRecyclerAdapter = new TablesRecyclerAdapter(list, this);
        recyclerView.setAdapter(tablesRecyclerAdapter);
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edtSearch.getWindowToken(), 0);
                Table table = new Table();
                table.setName(getAlphaNumericString());
                list.add(table);
                tablesRecyclerAdapter.customNotifyDataSetChanged(list);
                linearLayoutManager.scrollToPosition(list.size()-1);
            }
        });
        toolbar_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edtSearch.getWindowToken(), 0);
                edtSearch.setText("");
                tablesRecyclerAdapter.shouldEdit(isEditing = !isEditing);
                if(isEditing) {
                    edtSearch.setVisibility(View.GONE);
                    imgAdd.setVisibility(View.GONE);
                    toolbar_edit.setText(getString(R.string.done));
                    toolbar_title.setText(getString(R.string.selected, selectedItemCount));
                    listener.toggleBottomView(false);
                } else {
                    toolbar_edit.setText(getString(R.string.edit));
                    edtSearch.setVisibility(View.VISIBLE);
                    imgAdd.setVisibility(View.VISIBLE);
                    toolbar_title.setText(getString(R.string.tables));
                    listener.toggleBottomView(true);
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=list.size()-1;i>=0;i--) {
                    if(list.get(i).isSelected()) {
                        list.remove(i);
                    }
                }
                tablesRecyclerAdapter.customNotifyDataSetChanged(list);
                selectedItemCount = 0;
                toolbar_title.setText(getString(R.string.selected, selectedItemCount));
                btnDelete.setVisibility(View.GONE);
            }
        });
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tablesRecyclerAdapter.getFilter().filter(charSequence.toString());
            }
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) { }
        });
        return rootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof MainActivityPresenter.View) {
            listener = (MainActivityPresenter.View) context;
        }
    }

    private Comparator<Table> mapComparator = new Comparator<Table>() {
        public int compare(Table m1, Table m2) {
            return m1.getName().compareTo(m2.getName());
        }
    };
    private String getAlphaNumericString() {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(11);
        for (int i = 0; i < 11; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }
    private String sampleJson = "[\n" +
            "{\n" +
            "\"name\" : \"Grapes\"\n" +
            "}, {\n" +
            "\"name\" : \"Banana\" },\n" +
            "{\n" +
            "\"name\" : \"Pineapple\"\n" +
            "}, {\n" +
            "\"name\" : \"Coconut\" },\n" +
            "{\n" +
            "\"name\" : \"Cranberry\"\n" +
            "}, {\n" +
            "\"name\" : \"Pear\" },\n" +
            "{\n" +
            "\"name\" : \"Plum\"\n" +
            "}, {\n" +
            "\"name\" : \"Pomegranate\" },\n" +
            "{\n" +
            "\"name\" : \"Peach\"\n" +
            "}, {\n" +
            "\"name\" : \"Soursop\" },\n" +
            "{\n" +
            "\"name\" : \"Passionfruit\"\n" +
            "}, {\n" +
            "\"name\" : \"Jackfruit\" },\n" +
            "{\n" +
            "\"name\" : \"Cloudberry\"\n" +
            "}]";

    @Override
    public void onClick(boolean value) {
        if(value) {
            selectedItemCount++;
        } else {
            selectedItemCount--;
        }
        if(isEditing && selectedItemCount > 0) {
            btnDelete.setVisibility(View.VISIBLE);
        } else {
            btnDelete.setVisibility(View.GONE);
        }
        toolbar_title.setText(getString(R.string.selected, selectedItemCount));
    }

    @Override
    public void gotoTableDetails(int position) {
        Intent intent = new Intent(getContext(), TablesDetailsActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("data", list.get(position));
        startActivityForResult(intent, REQUEST_CODE_EDIT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && REQUEST_CODE_EDIT == requestCode && data != null) {
            int position = data.getIntExtra("position", -1);
            String text = data.getStringExtra("data");
            if(!TextUtils.isEmpty(text)) {
                list.get(position).setName(text);
            } else {
                list.remove(position);
            }
            tablesRecyclerAdapter.customNotifyDataSetChanged(list);
        }
    }
}
