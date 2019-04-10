package com.sampledemo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sampledemo.R;
import com.sampledemo.models.Item;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;

public class FavoriteRecyclerAdapter extends RecyclerView.Adapter<FavoriteRecyclerAdapter.ViewHolder> {
    RealmResults<Item> data;
    public FavoriteRecyclerAdapter(RealmResults<Item> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public FavoriteRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view, parent, false);
        return new FavoriteRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteRecyclerAdapter.ViewHolder holder, int position) {
        holder.txtItemName.setText(data.get(position).getName());
        holder.txtItemDescription.setText(data.get(position).getDescription());
        holder.txtItemCategory.setText(data.get(position).getCategory());
        if(data.get(position).getFavorite()) {
            holder.imgFavorite.setImageResource(R.drawable.ic_baseline_star_24px);
        } else {
            holder.imgFavorite.setImageResource(R.drawable.ic_baseline_star_border_24px);
        }
        holder.imgFavorite.setTag(position);
        holder.imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                Item item = realm.where(Item.class).equalTo("id", data.get((int)view.getTag()).getId()).findFirst();
                item.setFavorite(!data.get((int)view.getTag()).getFavorite());
                realm.insertOrUpdate(item);
                realm.commitTransaction();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtItemName, txtItemDescription, txtItemCategory;
        ImageView imgFavorite;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtItemName = itemView.findViewById(R.id.txtItemName);
            txtItemDescription = itemView.findViewById(R.id.txtItemDescription);
            txtItemCategory = itemView.findViewById(R.id.txtItemCategory);
            imgFavorite = itemView.findViewById(R.id.imgFavorite);
        }
    }
}
