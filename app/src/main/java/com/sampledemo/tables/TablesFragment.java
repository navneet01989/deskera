package com.sampledemo.tables;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sampledemo.R;
import com.sampledemo.adapters.TablesRecyclerAdapter;
import com.sampledemo.models.Table;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TablesFragment extends Fragment implements TablesPresenter.OnItemClick {
    private boolean isEditing = false;
    private int selectedItemCount = 0;
    private TextView toolbar_title;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tables_fragment, container, false);
        final ImageView imgAdd = rootView.findViewById(R.id.imgAdd);
        final TextView toolbar_edit = rootView.findViewById(R.id.toolbar_edit);
        toolbar_title = rootView.findViewById(R.id.toolbar_title);
        final EditText edtSearch = rootView.findViewById(R.id.edtSearch);
        final RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        Type listType = new TypeToken<List<Table>>() {}.getType();
        final List<Table> list = new Gson().fromJson(sampleJson, listType);
        Collections.sort(list, mapComparator);
        final TablesRecyclerAdapter tablesRecyclerAdapter = new TablesRecyclerAdapter(list, this);
        recyclerView.setAdapter(tablesRecyclerAdapter);
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Table table = new Table();
                table.setName(getAlphaNumericString());
                list.add(table);
                linearLayoutManager.scrollToPosition(list.size()-1);
            }
        });
        toolbar_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tablesRecyclerAdapter.shouldEdit(isEditing = !isEditing);
                if(isEditing) {
                    edtSearch.setVisibility(View.GONE);
                    imgAdd.setVisibility(View.GONE);
                    toolbar_edit.setText(getString(R.string.done));
                    toolbar_title.setText(getString(R.string.selected, selectedItemCount));
                } else {
                    toolbar_edit.setText(getString(R.string.edit));
                    edtSearch.setVisibility(View.VISIBLE);
                    imgAdd.setVisibility(View.VISIBLE);
                    toolbar_title.setText(getString(R.string.tables));
                }
            }
        });
        return rootView;
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
        toolbar_title.setText(getString(R.string.selected, selectedItemCount));
    }
}