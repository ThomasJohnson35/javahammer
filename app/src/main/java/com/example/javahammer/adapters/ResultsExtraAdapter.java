package com.example.javahammer.adapters;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javahammer.R;
import com.example.javahammer.data.Weapon;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

public class ResultsExtraAdapter extends RecyclerView.Adapter<ResultsExtraAdapter.ViewHolder> {

    public Context context;
    public ArrayList<String> resultsExtraArrayList;
    public ResultsExtraAdapter(ArrayList<String> resultsExtraArrayList) {
        this.resultsExtraArrayList = resultsExtraArrayList;
    }
    @NonNull
    @Override
    public ResultsExtraAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_results_extra, parent, false);

        // Return a new holder instance
        ResultsExtraAdapter.ViewHolder viewHolder = new ResultsExtraAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ResultsExtraAdapter.ViewHolder holder, int position) {
        holder.name.setText(resultsExtraArrayList.get(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return resultsExtraArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_tv_item_results_extra);
        }
    }

}
