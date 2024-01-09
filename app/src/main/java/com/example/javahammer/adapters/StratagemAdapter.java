package com.example.javahammer.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javahammer.R;
import com.example.javahammer.data.Timing;
import com.example.javahammer.interfaces.StratagemListener;
import com.example.javahammer.data.Stratagem;

import java.util.ArrayList;

public class StratagemAdapter extends RecyclerView.Adapter<StratagemAdapter.ViewHolder> {

    private Context context;

    private ArrayList<Stratagem> stratagemArrayList;

    private StratagemListener listener;

    public StratagemAdapter(ArrayList<Stratagem> stratagemArrayList) {
        this.stratagemArrayList = stratagemArrayList;
    }


    public void setListener(StratagemListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public StratagemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_stratagem, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StratagemAdapter.ViewHolder holder, int position) {

        Stratagem stratagem = stratagemArrayList.get(position);

        holder.name.setText("" +stratagem.name);
        holder.cp.setText("" +stratagem.commandPointCost +" CP");
        holder.detachment.setText("" + listener.getDetachment());
        holder.type.setText(stratagem.stratagemType.toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onStratagemClick(stratagem);
            }
        });

        if (stratagem.alertTiming == null) {
            holder.layout.setBackgroundColor(context.getResources().getColor(R.color.white));
        } else {
            // If can be activated on either turn
            if (stratagem.alertTiming.stream().anyMatch(n -> n.playerTurn.equals(Timing.PlayerTurn.NONE))) {
                holder.layout.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.sea_green)));
            } else if (stratagem.alertTiming.stream().anyMatch(n -> n.playerTurn.equals(Timing.PlayerTurn.OPPONENT_TURN))) {
                holder.layout.setBackgroundColor(context.getResources().getColor(R.color.red));
            } else{
                holder.layout.setBackgroundColor(context.getResources().getColor(R.color.astartes_blue));
            }

            Pair<Integer, Integer> imageRes = stratagem.getImageRes();

            if (imageRes == null) {
                throw new IllegalArgumentException();
            } else if (imageRes.first == null) {
                holder.phaseOneIv.setVisibility(View.INVISIBLE);
                holder.phaseTwoIv.setVisibility(View.INVISIBLE);
            } else if (imageRes.second == null) {
                holder.phaseTwoIv.setImageResource(imageRes.first);
                holder.phaseTwoIv.setVisibility(View.INVISIBLE);
            } else {
                holder.phaseOneIv.setImageResource(imageRes.first);
                holder.phaseTwoIv.setImageResource(imageRes.second);
            }
        }




    }

    @Override
    public int getItemCount() {
        return stratagemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout layout;
        public TextView name, cp, detachment, type;
        public ImageView phaseOneIv, phaseTwoIv;
        public RecyclerView wargearRv;

        public ViewHolder(View itemView) {
            super(itemView);

            layout = itemView.findViewById(R.id.stratagem_layout);
            name = itemView.findViewById(R.id.stratagem_name_tv);

            cp = itemView.findViewById(R.id.stratagem_cp_tv);
            detachment = itemView.findViewById(R.id.stratagem_detachment_tv);
            type = itemView.findViewById(R.id.stratagem_type_tv);

            phaseOneIv = itemView.findViewById(R.id.item_stratagem_phase_symbol_one_iv);
            phaseTwoIv = itemView.findViewById(R.id.item_stratagem_phase_symbol_two_iv);
        }
    }


}
