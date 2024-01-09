package com.example.javahammer.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javahammer.R;
import com.example.javahammer.data.WargearOption;
import com.example.javahammer.activities.DatasheetBlowup;
import com.example.javahammer.fragments.DatasheetBlowupFragment;

import java.util.ArrayList;

public class WargearOptionAdapter extends RecyclerView.Adapter<WargearOptionAdapter.ViewHolder>{

    private Context context;
    private DatasheetBlowupFragment listener;
    private ArrayList<WargearOption> wargearOptionArrayList;

    public WargearOptionAdapter(ArrayList<WargearOption> wargearOptionArrayList) {
        this.wargearOptionArrayList = wargearOptionArrayList;
    }
    @NonNull
    @Override
    public WargearOptionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_wargear_option, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WargearOptionAdapter.ViewHolder holder, int position) {
        WargearOption wargearOption = wargearOptionArrayList.get(position);

        holder.name.setText(wargearOption.toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("Thomas", this.toString());
                listener.wargearOptionOnCLick(wargearOption);
            }
        });
    }
    public void setListener(DatasheetBlowupFragment listener) {
       this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return wargearOptionArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.wargear_option_name_tv);
        }
    }
}
