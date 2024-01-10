package com.example.javahammer.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javahammer.R;
import com.example.javahammer.data.Roster;
import com.example.javahammer.data.Unit;
import com.example.javahammer.data.Weapon;
import com.example.javahammer.interfaces.WeaponAdapterListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WeaponAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<ArrayList<Weapon>> weaponProfiles;
    private WeaponAdapterListener listener;

    // Pass in the contact array into the constructor
    public WeaponAdapter(ArrayList<ArrayList<Weapon>> weaponProfiles) {
        this.weaponProfiles = weaponProfiles;
    }

    public void setListener(WeaponAdapterListener listener) {
        this.listener = listener;
    }

    public void setFilteredList(ArrayList<ArrayList<Weapon>> filteredList) {
        this.weaponProfiles = filteredList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        if (viewType == 0) {
            // Inflate the custom layout
            View contactView = inflater.inflate(R.layout.item_weapon_profile, parent, false);
            return new SingleWeaponProfile(contactView);
        } else {
            // Inflate the custom layout
            View contactView = inflater.inflate(R.layout.item_multi_weapon_profile, parent, false);
            return new MultiWeaponProfile(contactView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return weaponProfiles.get(position).size() == 1 ? 0 : 1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setBackgroundColor(ContextCompat.getColor(context, (position % 2 == 1) ? R.color.white : R.color.warm_grey));
        if (holder.getItemViewType() == 0) {
            SingleWeaponProfile newHolder = (SingleWeaponProfile) holder ;
            Weapon weaponProfile = weaponProfiles.get(position).get(0);

            // Binds holder's TextView fields with the corresponding weaponProfiles stats
            //    holder.quanitiy.setText("" +weaponProfile.getQuanitiy());
            newHolder.name.setText("" + weaponProfile.getName());
            if (weaponProfile.getKeywords().isEmpty()) {
                newHolder.keywords.setVisibility(View.GONE);
            } else {
                newHolder.keywords.setText("" + weaponProfile.getKeywords());
            }
            newHolder.range.setText(weaponProfile.getRange() == 0 ? "Melee" : String.format("%d\"", weaponProfile.getRange()));
            newHolder.attacks.setText("" + weaponProfile.getAttacks());
            newHolder.bs.setText("" + weaponProfile.getBs() +"+");
            newHolder.strength.setText("" + weaponProfile.getStrength());
            if (weaponProfile.getAp() == 0) {
                newHolder.ap.setText("0");
            } else {
                newHolder.ap.setText("-" + weaponProfile.getAp());
            }

            newHolder.damage.setText("" + weaponProfile.getDamage());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onWeaponClick(weaponProfile);
                }
            });

        } else {
            MultiWeaponProfile newHolder = (MultiWeaponProfile) holder;

            // Flattens the list of profiles
            for (Weapon weapon : weaponProfiles.get(position)) {

            }

            class SimpleWeaponAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

                ArrayList<Weapon> weaponProfileList;

                @NonNull
                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    context = parent.getContext();
                    LayoutInflater inflater = LayoutInflater.from(context);
                    View contactView = inflater.inflate(R.layout.item_weapon_profile, parent, false);
                    return new SingleWeaponProfile(contactView);
                }

                @Override
                public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

                    SingleWeaponProfile newHolder = (SingleWeaponProfile) holder;
                    Weapon weaponProfile = weaponProfileList.get(position);

                    newHolder.arrow.setVisibility(View.VISIBLE);

                    newHolder.name.setText("" + weaponProfile.getName());
                    if (weaponProfile.getKeywords().isEmpty()) {
                        newHolder.keywords.setVisibility(View.GONE);
                    } else {
                        newHolder.keywords.setText("" + weaponProfile.getKeywords());
                    }
                    newHolder.range.setText(weaponProfile.getRange() == 0 ? "Melee" : String.format("%d\"", weaponProfile.getRange()));
                    newHolder.attacks.setText("" + weaponProfile.getAttacks());
                    newHolder.bs.setText("" + weaponProfile.getBs() +"+");
                    newHolder.strength.setText("" + weaponProfile.getStrength());
                    if (weaponProfile.getAp() == 0) {
                        newHolder.ap.setText("0");
                    } else {
                        newHolder.ap.setText("-" + weaponProfile.getAp());
                    }

                    newHolder.damage.setText("" + weaponProfile.getDamage());


                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            listener.onWeaponClick(weaponProfile);
                        }
                    });
                }

                @Override
                public int getItemCount() {
                    return weaponProfileList.size();
                }

                SimpleWeaponAdapter(ArrayList<Weapon> weaponProfileList) {
                    this.weaponProfileList = weaponProfileList;
                }
            }

            // Creates a new WeaponAdapter to display all the profiles
            SimpleWeaponAdapter weaponAdapter = new SimpleWeaponAdapter(weaponProfiles.get(position));
            newHolder.rv.setAdapter(weaponAdapter);
            newHolder.rv.setLayoutManager(new LinearLayoutManager(context));


        }


    }

    @Override
    public int getItemCount() {
        return weaponProfiles.size();
    }

    public class SingleWeaponProfile extends RecyclerView.ViewHolder {
        public TextView quanitiy;

        public TextView name;
        public TextView keywords;
        public TextView range;
        public TextView attacks;
        public TextView bs;
        public TextView strength;
        public TextView ap;
        public TextView damage;
        public ImageView arrow;

       // public TextView objectiveControl;

        public SingleWeaponProfile(View itemView) {

            super(itemView);

            Log.v("WeaponAdapter.ViewHolder", "constructor start");
            this.arrow = itemView.findViewById(R.id.item_weapon_arrow_iv);
            quanitiy = itemView.findViewById(R.id.weapon_quantity_tv);
            name = itemView.findViewById(R.id.name_tv);
            keywords = itemView.findViewById(R.id.keywords_tv);
            range = itemView.findViewById(R.id.range_tv);
            attacks = itemView.findViewById(R.id.attacks_tv);
            bs = itemView.findViewById(R.id.bs_tv);
            strength = itemView.findViewById(R.id.strength_tv);
            ap = itemView.findViewById(R.id.ap_tv);
            damage = itemView.findViewById(R.id.damage_tv);
            Log.v("StatlineAdapter.ViewHolder", "constructor done");

        }
    }
    public class MultiWeaponProfile extends RecyclerView.ViewHolder {
        public RecyclerView rv;

        public MultiWeaponProfile(View itemView) {

            super(itemView);

            Log.v("WeaponAdapter.ViewHolder", "constructor start");
            rv = itemView.findViewById(R.id.item_multi_weapon_profile_rv);
            Log.v("StatlineAdapter.ViewHolder", "constructor done");

        }
    }



}
