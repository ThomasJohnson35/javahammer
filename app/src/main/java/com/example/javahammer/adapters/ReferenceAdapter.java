package com.example.javahammer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javahammer.R;
import com.example.javahammer.data.Faction;
import com.example.javahammer.interfaces.ReferenceAdapterListener;

import java.util.ArrayList;

public class ReferenceAdapter extends RecyclerView.Adapter<ReferenceAdapter.ViewHolder> {

public Context context;
    public ArrayList<String> factionArrayList;

    public ReferenceAdapterListener listener;

    public ReferenceAdapter(ArrayList<String> factionArrayList) {
        this.factionArrayList = factionArrayList;
    }

    public void setListener(ReferenceAdapterListener listener) {
        this.listener = listener;
    }
    @NonNull
    @Override
    public ReferenceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_reference, parent, false);

        // Return a new holder instance
        ReferenceAdapter.ViewHolder viewHolder = new ReferenceAdapter.ViewHolder(contactView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ReferenceAdapter.ViewHolder holder, int position) {

        String str = factionArrayList.get(position);
        holder.name.setText(str);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return factionArrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.item_reference_name_tv);
        }
    }

}
