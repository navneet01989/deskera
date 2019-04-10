package com.sampledemo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.internal.LinkedTreeMap;
import com.sampledemo.R;
import com.sampledemo.models.Table;
import com.sampledemo.tables.TablesPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TablesRecyclerAdapter extends RecyclerView.Adapter<TablesRecyclerAdapter.ViewHolder> {
    private List<Table> list;
    private TablesPresenter.View listener;
    private boolean isEditing = false;
    public TablesRecyclerAdapter(List<Table> list, TablesPresenter.View listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tables_recycler_view, parent, false);
        return new TablesRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.txtFruitName.setText(list.get(position).getName());
        if(isEditing) {
            holder.cbkSelect.setVisibility(View.VISIBLE);
            holder.childParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list.get(position).setSelected(!list.get(position).isSelected());
                    notifyItemChanged(position);
                    listener.onClick(list.get(position).isSelected());
                }
            });
            holder.cbkSelect.setChecked(list.get(position).isSelected());
        } else {
            holder.childParent.setOnClickListener(null);
            holder.cbkSelect.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtFruitName;
        CheckBox cbkSelect;
        LinearLayout childParent;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtFruitName = itemView.findViewById(R.id.txtFruitName);
            cbkSelect = itemView.findViewById(R.id.cbkSelect);
            childParent = itemView.findViewById(R.id.childParent);
        }
    }
    public void shouldEdit(boolean isEditing) {
        this.isEditing = isEditing;
        notifyDataSetChanged();
    }
}
