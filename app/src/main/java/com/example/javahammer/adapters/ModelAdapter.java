package com.example.javahammer.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javahammer.data.ModelComposition;
import com.example.javahammer.R;
import com.example.javahammer.activities.DatasheetBlowup;
import com.example.javahammer.data.Model;
import com.example.javahammer.interfaces.ModelAdapterListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ModelAdapter extends RecyclerView.Adapter<ModelAdapter.ViewHolder> {

    // FIELDS
    private Context context;
    protected DatasheetBlowup datasheetBlowup;
    private ArrayList<ModelComposition> unitComposition;
    protected WargearAdapter wargearAdapter;
    protected RecyclerView wargearRv;
    protected ModelAdapterListener listener;

    // CONSTRUCTOR
    public ModelAdapter(ArrayList<ModelComposition> modelCompositionArrayList) {
        this.unitComposition = modelCompositionArrayList;
    }

    // METHODS
    public void setListener(ModelAdapterListener listener) {
        this.listener = listener;
    }
    @NonNull
    @Override
    public ModelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_model_composition, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ModelAdapter.ViewHolder holder, int position) {

        ModelComposition modelComposition = unitComposition.get(position);
        Log.v("Thomas", this.toString());

        // Binds holder's TextView fields with the corresponding weapons stats
        holder.name.setText("" +  modelComposition.getNumberOfModels() +"X "+modelComposition.getTemplateModel().getName());
        wargearAdapter = new WargearAdapter(modelComposition.getLoadoutCompositon());
        wargearRv = holder.wargearRv;
        wargearRv.setAdapter(wargearAdapter);
        wargearRv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        // If model count is at the maximum
        if (modelComposition.getNumberOfModels() >= modelComposition.getMax()) {
            holder.addModelBtn.setVisibility(View.INVISIBLE);
        } else {
            holder.addModelBtn.setVisibility(View.VISIBLE);
        }
        holder.addModelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("Thomas", this.toString());
                modelComposition.addModel(modelComposition.getTemplateModel(), 1);
                listener.unitDataChanged();
            }
        });
    }
    @Override
    public int getItemCount() {
        return unitComposition.size();
    }
    public void setDatasheetBlowup(DatasheetBlowup datasheetBlowup) {
        this.datasheetBlowup = datasheetBlowup;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public RecyclerView wargearRv;
        public ImageButton addModelBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.model_name_tv);
            wargearRv = itemView.findViewById(R.id.wargear_rv);
            addModelBtn = itemView.findViewById(R.id.increase_model_count_btn);
        }
    }
    class WargearAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        HashMap<Model, Integer> modelWargearHashMap;
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            // Return a new holder instance
            if (viewType == 0) {
                View contactView = inflater.inflate(R.layout.item_model, parent, false);
                return new WargearHolder(contactView);
            } else {
                View contactView = inflater.inflate(R.layout.item_wargear_choice, parent, false);
                return new WargearChoiceHolder(contactView);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            Model model = (Model) modelWargearHashMap.keySet().toArray()[position];
            Integer count = (Integer) modelWargearHashMap.values().toArray()[position];
            WargearHolder wh = (WargearHolder) holder;

            // Cast to single wargear item with no choice
            wh.name.setText(String.format("%dX %s", count,model.getWargear()));
            wh.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onModelClick(model);
                }
            });
        }

        @Override
        public int getItemViewType(int position) {return 0;}
        @Override
        public int getItemCount() {
            return modelWargearHashMap.size();
        }
        class WargearHolder extends RecyclerView.ViewHolder {

            public TextView name;
            public WargearHolder(View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.wargear_name_tv);
            }
        }
        class WargearChoiceHolder extends RecyclerView.ViewHolder {
            public TextView name;
            public ImageView leftBtn;
            public ImageView rightBtn;

            public WargearChoiceHolder(View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.wargear_name_tv);
                leftBtn = itemView.findViewById(R.id.left_btn);
                rightBtn = itemView.findViewById(R.id.right_btn);
            }
        }
        public WargearAdapter(HashMap<Model, Integer> wargearHashMap) {this.modelWargearHashMap = wargearHashMap;}
    }

    /*
    class ExchangeableWargearAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        LinkedHashMap<ArrayList<Weapon>, Integer> wargearChoicesMap;

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            // Return a new holder instance
            if (viewType == 0) {
                View contactView = inflater.inflate(R.layout.item_wargear, parent, false);
                return new ModelHolder(contactView);
            } else {
                View contactView = inflater.inflate(R.layout.item_wargear_choice, parent, false);
                return new WargearChoiceHolder(contactView);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            Map.Entry<ArrayList<Weapon>, Integer> entry = getEntry(position);

            // Cast to single wargear item with no choice
            if (getItemViewType(position) == 0) {
                ModelHolder wh = (ModelHolder) holder;
                wh.name.setText("" +entry.getKey().get(0).getName());
            } else {
                WargearChoiceHolder wch = (WargearChoiceHolder) holder;
                wch.name.setText("" +entry.getKey().get(entry.getValue()).getName());

                wch.leftBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        entry.getKey().get(entry.getValue()).quanitiy--;
                        if (entry.getValue() == 0) {
                            entry.setValue(entry.getKey().size()-1);
                        } else {
                            entry.setValue(entry.getValue() - 1);
                        }
                        entry.getKey().get(entry.getValue()).quanitiy++;
                        wch.name.setText("" +entry.getKey().get(entry.getValue()).getName());
                        if (listener != null) {
                            listener.defenderAdapter.notifyDataSetChanged();
                        }
                        datasheetBlowup.weaponAdapter.notifyDataSetChanged();
                    }
                });

                wch.rightBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        entry.getKey().get(entry.getValue()).quanitiy--;
                        if (entry.getValue() == entry.getKey().size()-1) {
                            entry.setValue(0);
                        } else {
                            entry.setValue(entry.getValue() + 1);
                        }
                        entry.getKey().get(entry.getValue()).quanitiy++;
                        wch.name.setText("" +entry.getKey().get(entry.getValue()).getName());
                        if (listener != null) {
                            listener.defenderAdapter.notifyDataSetChanged();
                        }
                        datasheetBlowup.weaponAdapter.notifyDataSetChanged();

                    }
                });
            }

        }

        public Map.Entry<ArrayList<Weapon>, Integer> getEntry(int position) {

            Set<Map.Entry<ArrayList<Weapon>, Integer> > entrySet = wargearChoicesMap.entrySet();
            Iterator<Map.Entry<ArrayList<Weapon>, Integer> > iterator = entrySet.iterator();

            int index = 0;

            List<Weapon> wargearString;

            while (iterator.hasNext()) {

                //Log.v("ModelAdapter Iterator")
                if (index == position) {
                    return iterator.next();
                }
                iterator.next();
                index++;
            }
            return null;
        }

        @Override
        public int getItemViewType(int position) {

            if (getEntry(position).getKey().size() == 1) {
                return 0;
            }
            else {
                return 1;
            }
        }
        @Override
        public int getItemCount() {
            return wargearChoicesMap.size();
        }
        class ModelHolder extends RecyclerView.ViewHolder {

            public TextView name;
            public ModelHolder(View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.wargear_name_tv);
            }
        }

        class WargearChoiceHolder extends RecyclerView.ViewHolder {
            public TextView name;
            public ImageView leftBtn;
            public ImageView rightBtn;

            public WargearChoiceHolder(View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.wargear_name_tv);
                leftBtn = itemView.findViewById(R.id.left_btn);
                rightBtn = itemView.findViewById(R.id.right_btn);
            }
        }

        public ExchangeableWargearAdapter(LinkedHashMap<ArrayList<Weapon>, Integer> wargearChoicesMap) {
            this.wargearChoicesMap = (LinkedHashMap<ArrayList<Weapon>, Integer>) wargearChoicesMap;
        }
    }


     */
}
