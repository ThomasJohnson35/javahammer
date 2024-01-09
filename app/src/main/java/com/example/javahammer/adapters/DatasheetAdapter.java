package com.example.javahammer.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javahammer.R;
import com.example.javahammer.archived.OnItemClickListener;
import com.example.javahammer.data.Roster;
import com.example.javahammer.data.Unit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class DatasheetAdapter extends RecyclerView.Adapter<DatasheetAdapter.ViewHolder> {

    private Context context;
    private Roster roster;
    private ArrayList<Unit> units;
    private OnItemClickListener listener;


    // Pass in the contact array into the constructor
    public DatasheetAdapter(Roster roster, ArrayList<Unit> units) {
        this.roster = roster;
        this.units = units;
        Collections.sort(units);
        Log.v("DatasheetAdapter", "DatasheetList size: "+units.size());

        StringBuffer stringBuffer = new StringBuffer();

        for (Unit unit : units) {
            stringBuffer.append(unit.getName() +"\n");
        }

        Log.v("DatasheetAdapter", "Sorted Units List: "+stringBuffer.toString());
    }


    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public void setFilteredList(Roster roster, ArrayList<Unit> filteredList) {
        this.units = filteredList;
        this.roster = roster;
        Collections.sort(units);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DatasheetAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_datasheet, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(DatasheetAdapter.ViewHolder holder, int position) {

        Unit unit = units.get(position);
        holder.unitName.setText(unit.getName());
        ImageButton addBtn = holder.addBtn;

        holder.points.setText("" +unit.getPoints() +" Points");

        if (roster != null) {
            int numInArmy = roster.getUnits().stream().filter(n -> n.getName().equals(unit.getName())).collect(Collectors.toList()).size();

            holder.unitsInArmy.setText(String.format("%dX Unit in Army", numInArmy));
            holder.unitsInArmy.setVisibility(numInArmy == 0 ? View.INVISIBLE : View.VISIBLE);
        } else {
            addBtn.setVisibility(View.INVISIBLE);
        }


        holder.unitImage.setImageResource(unit.getImageRes());

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(unit);
                notifyItemChanged(holder.getAdapterPosition());
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onBlowupDatasheet(unit);
            }
        });
    }

    @Override
    public int getItemCount() {
        return units.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView unitName;
        public ImageButton addBtn;
        public TextView points, unitsInArmy;
        public ImageView unitImage;


        public ViewHolder(View itemView) {
            super(itemView);

            unitName = (TextView) (itemView.findViewById(R.id.datasheet_name_tv));
            addBtn = (ImageButton) itemView.findViewById(R.id.datasheet_add_btn);
            unitsInArmy = itemView.findViewById(R.id.item_datasheet_units_in_army_tv);
            points = itemView.findViewById(R.id.datasheet_points_tv);
            unitImage = itemView.findViewById(R.id.item_datasheet_unit_image);
        }
    }



}
