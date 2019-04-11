package com.sampledemo.favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sampledemo.R;
import com.sampledemo.adapters.FavoriteRecyclerAdapter;
import com.sampledemo.models.Item;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;

public class FavoriteFragment extends Fragment implements FavoriteRecyclerAdapterPresenter.View {
    public static Fragment newInstance() {
        return new FavoriteFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.favorite_fragment, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        TextView txtEmptyView = rootView.findViewById(R.id.txtEmptyView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Item> listFavorite = realm.where(Item.class).equalTo("isFavorite", true).findAll();
        recyclerView.setAdapter(new FavoriteRecyclerAdapter(listFavorite, this));
        if(recyclerView.getAdapter().getItemCount() == 0) {
            recyclerView.setVisibility(View.GONE);
            txtEmptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            txtEmptyView.setVisibility(View.GONE);
        }
        return rootView;
    }

    @Override
    public void refreshFragment() {
        if(getFragmentManager() != null) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commitAllowingStateLoss();
        }
    }
}
