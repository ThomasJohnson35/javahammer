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
import com.example.javahammer.archived.OnItemClickListener;
import com.example.javahammer.data.Weapon;

import java.util.HashSet;
import java.util.stream.Collectors;

public class KeywordAdapter extends RecyclerView.Adapter<KeywordAdapter.ViewHolder> {

    private HashSet<Weapon.wepAbilities> keywordSet;
    protected Context context;
    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public KeywordAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_keyword, parent, false);

        // Return a new holder instance
        KeywordAdapter.ViewHolder viewHolder = new KeywordAdapter.ViewHolder(contactView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(KeywordAdapter.ViewHolder holder, int position) {

        Weapon.Keywords keyword = Weapon.Keywords.values()[position];

        holder.name.setText(keyword.name());

        boolean activated = keywordSet.stream().anyMatch(n -> n.keyword.equals(keyword));
        holder.checkBox.setImageResource(activated ? R.drawable.checked_box : R.drawable.empty_box_icon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activated) {
                    keywordSet = new HashSet<>(keywordSet.stream().filter(n -> !(n.keyword.equals(keyword))).collect(Collectors.toSet()));
                } else {
                    keywordSet.add(new Weapon.wepAbilities(keyword));
                }
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return Weapon.Keywords.values().length;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_tv_item_keyword);
            checkBox = itemView.findViewById(R.id.is_activated_iv_item_keyword);
        }
    }

    // Pass in the contact array into the constructor
    public KeywordAdapter(HashSet<Weapon.wepAbilities>  keywordSet) {
        this.keywordSet = keywordSet;
    }


}