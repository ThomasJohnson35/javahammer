package com.example.javahammer.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javahammer.R;
import com.example.javahammer.activities.DatasheetBlowup;
import com.example.javahammer.data.Model;

public class ModelAdapter2 extends RecyclerView.Adapter<ModelAdapter2.ViewHolder> {

    // FIELDS
    private Context context;
    protected DatasheetBlowup listener;

    // CONSTRUCTOR
    public ModelAdapter2(DatasheetBlowup datasheetBlowup) {
        this.listener = datasheetBlowup;
    }

    // METHODS
    public void setWargearOption() {

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_model_composition, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Model model = (Model) listener.unit.getUnitModelComposition().keySet().toArray()[position];
        Integer count  = (Integer) listener.unit.getUnitModelComposition().values().toArray()[position];

        Log.v("Thomas", this.toString());

        // Binds holder's TextView fields with the corresponding weapons stats
        holder.name.setText(count +"X "+model.getName() + " " +model.getWargear());
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onModelClick(model);
            }
        });
    }
    @Override
    public int getItemCount() {
        return listener.unit.getUnitModelComposition().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public RecyclerView wargearRv;
        public ImageButton addModelBtn;
        public ImageButton removeModelBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.model_name_tv);
          //  wargearRv = itemView.findViewById(R.id.wargearRv);
            addModelBtn = itemView.findViewById(R.id.increase_model_count_btn);
        }
    }
}