package com.sampledemo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
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

public class TablesRecyclerAdapter extends RecyclerView.Adapter<TablesRecyclerAdapter.ViewHolder> implements Filterable {
    private List<Table> list;
    private List<Table> filterlist = new ArrayList<>();
    private TablesPresenter.View listener;
    private TableFilter tableFilter;
    private boolean isEditing = false;
    public TablesRecyclerAdapter(List<Table> list, TablesPresenter.View listener) {
        this.list = list;
        this.filterlist = list;
        this.listener = listener;
        tableFilter = new TableFilter(this);
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
            holder.cbkSelect.setChecked(list.get(position).isSelected());
        } else {
            holder.childParent.setOnClickListener(null);
            holder.cbkSelect.setVisibility(View.GONE);
        }
        holder.childParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEditing) {
                    list.get(position).setSelected(!list.get(position).isSelected());
                    notifyItemChanged(position);
                    listener.onClick(list.get(position).isSelected());
                } else {

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return tableFilter;
    }

    public void customNotifyDataSetChanged(List<Table> list) {
        this.filterlist = list;
        this.list = list;
        notifyDataSetChanged();
    }

    class TableFilter extends Filter {
        TablesRecyclerAdapter mAdapter;
        TableFilter(TablesRecyclerAdapter mAdapter){
            super();
            this.mAdapter = mAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Table> filterlist = new ArrayList<>();
            final FilterResults results = new FilterResults();
            if(charSequence.length() == 0) {
                filterlist.addAll(TablesRecyclerAdapter.this.filterlist);
            } else {
                final String filterPattern =charSequence.toString().toLowerCase().trim();
                for(Table tableItem : list){
                    if(tableItem.getName().toLowerCase().startsWith(filterPattern)){
                        filterlist.add(tableItem);
                    }
                }
            }
            results.values = filterlist;
            results.count = filterlist.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            TablesRecyclerAdapter.this.list = (List<Table>) filterResults.values;
            notifyDataSetChanged();
        }
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
