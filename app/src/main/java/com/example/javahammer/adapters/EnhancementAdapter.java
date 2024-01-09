package com.example.javahammer.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javahammer.R;
import com.example.javahammer.archived.OnItemClickListener;
import com.example.javahammer.data.Enhancement;
import com.example.javahammer.data.Roster;
import com.example.javahammer.data.Unit;
import com.example.javahammer.interfaces.EnhancementAdapterListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class EnhancementAdapter extends RecyclerView.Adapter<EnhancementAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Enhancement> enhancements;
    private EnhancementAdapterListener listener;

    // Pass in the contact array into the constructor
    public EnhancementAdapter(ArrayList<Enhancement> enhancements) {
        this.enhancements = enhancements;
    }

    public void setListener(EnhancementAdapterListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public EnhancementAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_enhancement, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(EnhancementAdapter.ViewHolder holder, int position) {

        Enhancement enhancement = enhancements.get(position);

        holder.name.setText(enhancement.getName());
        holder.points.setText(String.format("%s Points", +enhancement.getPoints()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEnhancementClicked(enhancement);
            }
        });
    }

    @Override
    public int getItemCount() {
        return enhancements.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, points;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.item_enhancement_name_tv);
            points = itemView.findViewById(R.id.item_enhancement_points_tv);
        }
    }




}