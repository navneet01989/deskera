package com.sampledemo.item;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sampledemo.R;
import com.sampledemo.adapters.ItemsRecyclerAdapter;
import com.sampledemo.models.Item;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;

public class ItemFragment extends Fragment {

    public static Fragment newInstance(int position) {
        ItemFragment itemFragment = new ItemFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        itemFragment.setArguments(bundle);
        return itemFragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            assert getFragmentManager() != null;
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_fragment, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        assert getArguments() != null;
        Realm realm = Realm.getDefaultInstance();
        switch (getArguments().getInt("position")) {
            case 0:
                RealmResults<Item> listAll = realm.where(Item.class).findAll();
                recyclerView.setAdapter(new ItemsRecyclerAdapter(listAll));
                break;
            case 1:
                RealmResults<Item> listCatA = realm.where(Item.class).equalTo("category", "Item Category A").findAll();
                recyclerView.setAdapter(new ItemsRecyclerAdapter(listCatA));
                break;
            case 2:
                RealmResults<Item> listCatB = realm.where(Item.class).equalTo("category", "Item Category B").findAll();
                recyclerView.setAdapter(new ItemsRecyclerAdapter(listCatB));
                break;
        }
        return rootView;
    }
}
