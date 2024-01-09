package com.example.javahammer.adapters;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javahammer.R;
import com.example.javahammer.data.Roster;
import com.example.javahammer.fragments.EditRosterSettingsFragment;
import com.example.javahammer.fragments.RosterBuilderFragment;
import com.example.javahammer.fragments.RosterLibraryFragment;
import com.example.javahammer.interfaces.RosterAdapterListener;

import java.util.ArrayList;

public class RosterLibraryAdapter extends RecyclerView.Adapter<RosterLibraryAdapter.ViewHolder> {

    ArrayList<Roster> rosterArrayList;
    RosterAdapterListener listener;
    public RosterLibraryAdapter(ArrayList<Roster> rosterArrayList) {
        this.rosterArrayList = rosterArrayList;
    }

    public void setListener(RosterAdapterListener listener) {
        this.listener = listener;
    }

    public void hideEllipsis() {

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View contactView = inflater.inflate(R.layout.item_roster, parent, false);
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Roster roster = rosterArrayList.get(position);

        holder.name.setText(roster.getName());
        // holder.name.setText("" +roster.getName());
        holder.description.setText(
                String.format("%s\n%s", roster.getFaction(), roster.getDetachment())
        );

        holder.points.setText(
                String.format("%d/\n%d PTS", roster.getPoints(), roster.getBattleSize().getPoints()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(roster);
            }
        });

        holder.ellipsis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(view.getContext(), view);

                listener.onEllipsisClick(roster, popup);



                popup.inflate(R.menu.roster_popup);
                popup.show();
            }
        });

        holder.imageView.setImageResource(roster.getFaction().getImageRes());
    }

    @Override
    public int getItemCount() {
        return rosterArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView description, name, points;
        ImageView imageView;
        ImageButton ellipsis;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_roster_name_tv);
            description = itemView.findViewById(R.id.item_roster_faction_tv);
            points = itemView.findViewById(R.id.item_roster_faction_points_tv);
            imageView = itemView.findViewById(R.id.item_roster_faction_iv);
            ellipsis = itemView.findViewById(R.id.item_roster_ellipsis_btn);
        }
    }
}
