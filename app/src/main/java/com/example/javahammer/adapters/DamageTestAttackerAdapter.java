package com.example.javahammer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javahammer.R;
import com.example.javahammer.data.Weapon;
import com.example.javahammer.interfaces.ProfileAdapterListener;

import java.util.ArrayList;

public class DamageTestAttackerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public Context context;
    ArrayList<Weapon> weaponArrayList;

    public ProfileAdapterListener listener;
    public int selected = 0;

    public DamageTestAttackerAdapter(ArrayList<Weapon> weaponArrayList) {
        this.weaponArrayList = weaponArrayList;
    }

    public void setListener(ProfileAdapterListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        switch (viewType) {
            case 0:
                return new AddWeaponHolder(inflater.inflate(R.layout.item_damage_test_profile, parent, false));
            case 1:
                return new PlusHolder(inflater.inflate(R.layout.item_damage_test_plus, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case 0:
                Weapon weapon = weaponArrayList.get(position);
                AddWeaponHolder addWeaponHolder = (AddWeaponHolder) holder;
                addWeaponHolder.name.setText(weapon.getName());
                addWeaponHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selected = holder.getAdapterPosition();
                        listener.onWeaponClicked();
                        notifyDataSetChanged();
                    }
                });

                // Tints the item blue and turns text white if it is the currently selected item
                if (holder.getAdapterPosition() == selected) {
                    addWeaponHolder.innerLayout.setBackgroundTintList(context.getResources().getColorStateList(R.color.astartes_blue));
                    addWeaponHolder.name.setTextColor(context.getResources().getColor(R.color.white));
                } else {
                    addWeaponHolder.innerLayout.setBackgroundTintList(context.getResources().getColorStateList(R.color.white));
                    addWeaponHolder.name.setTextColor(context.getResources().getColor(R.color.black));
                }

                break;
            case 1:
                PlusHolder plusHolder = (PlusHolder) holder;
                plusHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onAttackerPlusClicked();
                    }
                });
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {

        return position == getItemCount()-1 ? 1 : 0;
    }

    @Override
    public int getItemCount() {
        return weaponArrayList.size() + 1;
    }

    public class AddWeaponHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public ConstraintLayout innerLayout;

        public AddWeaponHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.item_damage_test_profile_name_tv);
            innerLayout = itemView.findViewById(R.id.item_damagE_test_profile_inner_layout);
        }

    }

    public class PlusHolder extends RecyclerView.ViewHolder {
        public PlusHolder(View itemView) {
            super(itemView);
        }
    }
 }
