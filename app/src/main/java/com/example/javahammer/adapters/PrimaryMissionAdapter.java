package com.example.javahammer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javahammer.R;
import com.example.javahammer.data.MissionCard;

import java.util.ArrayList;

public class PrimaryMissionAdapter extends RecyclerView.Adapter<PrimaryMissionAdapter.ViewHolder> {
    ArrayList<MissionCard> missionCardList;
    Context context;

    @NonNull
    @Override
    public PrimaryMissionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.mission_card, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PrimaryMissionAdapter.ViewHolder holder, int position) {

        MissionCard missionCard = missionCardList.get(position);
        holder.image = new ImageView(context);
        holder.image.setImageResource(missionCard.imageRes);
    }

    @Override
    public int getItemCount() {

        if (missionCardList == null) {
            return 0;
        } else {
            return missionCardList.size();
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.mission_card_iv);
        }
    }

    public PrimaryMissionAdapter(ArrayList<MissionCard> missionCardList) {
        this.missionCardList = missionCardList;
    }
}
