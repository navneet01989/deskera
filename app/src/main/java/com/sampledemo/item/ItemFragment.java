package com.sampledemo.item;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sampledemo.R;
import com.sampledemo.adapters.ItemsRecyclerAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ItemFragment extends Fragment {

    public static Fragment newInstance(int position) {
        ItemFragment itemFragment = new ItemFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        itemFragment.setArguments(bundle);
        return itemFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_fragment, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new ItemsRecyclerAdapter());
        return rootView;
    }
}
