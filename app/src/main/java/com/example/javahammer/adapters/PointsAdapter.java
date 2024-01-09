package com.example.javahammer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javahammer.R;
import com.example.javahammer.data.PointsThreshold;
import com.example.javahammer.data.Unit;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PointsAdapter extends RecyclerView.Adapter<PointsAdapter.ViewHolder>{

    private Context context;
    public Unit unit;
    public PointsAdapter(Unit unit) {
        this.unit = unit;
    //    this.pointsThresholdArrayList = pointsThresholdArrayList;
    }
    @NonNull
    @Override
    public PointsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater =LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.item_points_and_units, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PointsAdapter.ViewHolder holder, int position) {

        PointsThreshold pointsThreshold = unit.getPointsMap().get(position);

        holder.name.setText(String.format("%d Models", pointsThreshold.numberModels));
        holder.points.setText(String.format("%d Points", pointsThreshold.points));

        if (unit.getPoints() == pointsThreshold.points) {
          //  holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.warm_grey));
            holder.points.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
        } else {
            holder.points.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
        }
    }

    @Override
    public int getItemCount() {
        return unit.getPointsMap().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView points;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_tv);
            points = itemView.findViewById(R.id.points_tv);

        }
    }
}
