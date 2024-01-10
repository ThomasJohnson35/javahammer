package com.example.javahammer.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javahammer.R;
import com.example.javahammer.activities.DatasheetBlowup;
import com.example.javahammer.data.Model;
import com.example.javahammer.data.ModelComposition;
import com.example.javahammer.data.Unit;
import com.example.javahammer.data.Wargear;
import com.example.javahammer.data.WargearOption;
import com.example.javahammer.interfaces.ModelAdapterListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ModelCompositionAdapter extends RecyclerView.Adapter<ModelCompositionAdapter.ViewHolder>{

    // FIELDS
    private Context context;
    protected DatasheetBlowup datasheetBlowup;
    private ArrayList<ModelComposition> unitComposition;
    protected ModelAdapter modelAdapter;
    protected RecyclerView modelRv;
    protected ModelAdapterListener listener;
    protected Unit unit;

    // CONSTRUCTOR
    public ModelCompositionAdapter(Unit unit) {
        this.unit = unit;
        this.unitComposition = unit.getUnitComposition();
    }

    // METHODS
    public void setListener(ModelAdapterListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ModelCompositionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_model_composition, parent, false);
        // Return a new holder instance
        ModelCompositionAdapter.ViewHolder viewHolder = new ModelCompositionAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ModelCompositionAdapter.ViewHolder holder, int position) {

        ModelComposition modelComposition = unitComposition.get(position);
        Log.v("Thomas", this.toString());

        // Binds holder's TextView fields with the corresponding weapons stats
        holder.name.setText("" + modelComposition.getNumberOfModels() + "X " + modelComposition.getTemplateModel().getName());
        modelAdapter = new ModelAdapter(modelComposition);
        modelRv = holder.wargearRv;
        modelRv.setAdapter(modelAdapter);
        modelRv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

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
        return unit.getUnitComposition().size();
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

    public class ModelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        HashMap<Model, Integer> modelWargearHashMap;
        ModelComposition modelComposition;
        public ModelAdapter(ModelComposition modelComposition) {
            this.modelComposition = modelComposition;
            this.modelWargearHashMap = modelComposition.getLoadoutCompositon();
        }
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            // Return a new holder instance
            View contactView = inflater.inflate(R.layout.item_model, parent, false);
            return new ModelHolder(contactView);

        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            Model model = (Model) modelWargearHashMap.keySet().toArray()[position];
            Integer count = (Integer) modelWargearHashMap.values().toArray()[position];
            ModelHolder wh = (ModelHolder) holder;

            // Cast to single wargear item with no choice

            wh.name.setText(String.format("%dX %s", count, model.getWargear()));

            wh.removeModelBtn.setVisibility(modelComposition.getMin() < modelComposition.getNumberOfModels() ? View.VISIBLE : View.GONE);
            wh.removeModelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.removeModel(modelComposition, model);
                    ModelCompositionAdapter.this.notifyDataSetChanged();
                }
            });

            WargearOptionAdapter woh = new WargearOptionAdapter(model);
            wh.wargearOptionRv.setAdapter(woh);
            wh.wargearOptionRv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
         //   wh.wargearOptionRv.

        }

        @Override
        public int getItemCount() {
            return modelWargearHashMap.size();
        }

        class ModelHolder extends RecyclerView.ViewHolder {

            public TextView name;
            public RecyclerView wargearOptionRv;
            public ImageButton removeModelBtn;
            public ModelHolder(View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.wargear_name_tv);
                wargearOptionRv = itemView.findViewById(R.id.item_wargear_rv);
                removeModelBtn = itemView.findViewById(R.id.remove_model_btn);
            }
        }
    }

    public class WargearOptionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        Model model;
        ArrayList<WargearOption> wargearOptionArrayList;
        public WargearOptionAdapter(Model model) {
            this.model = model;
            wargearOptionArrayList = new ArrayList();
            for (WargearOption wargearOption : unit.getWargearOptions()) {
                if (wargearOption.getRequiredModel().equals("") || wargearOption.getRequiredModel().equals(model.getName())) {
                    wargearOptionArrayList.add(wargearOption);
                }
            }
        }
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            // Return a new holder instance

            View contactView = inflater.inflate(R.layout.item_wargear_option_alt, parent, false);
            return new WargearOptionHolder(contactView);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            WargearOption wargearOption = wargearOptionArrayList.get(position);
            WargearOptionHolder woh = (WargearOptionHolder) holder;

            ArrayAdapter<ArrayList<Wargear>> wargearOptionAdapter = new ArrayAdapter(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, wargearOption.getExchangeableWargear());
            woh.spinner.setAdapter(wargearOptionAdapter);
            for (Map.Entry<Model, Integer> entry : wargearOption.activeModels.entrySet()) {
                if (entry.getKey().equals(model)) {
                    woh.spinner.setSelection(entry.getValue());
                }
            }

            Log.v("Thomas", model.getWargear() + "\n" +woh.spinner.getSelectedItem() +"\n" +model.getWargear().contains(woh.spinner.getSelectedItem()));
            Log.v("Thomas", String.valueOf(model.getWargear().containsAll((Collection<?>) woh.spinner.getSelectedItem())));
            woh.spinner.setEnabled(model.getWargear().containsAll((Collection<?>) woh.spinner.getSelectedItem()));
            woh.spinner.setOnItemSelectedListener(new WargearOptionSpinner(model, wargearOption, woh.spinner.getSelectedItemPosition()));
        }

        @Override
        public int getItemCount() {
            return wargearOptionArrayList.size();
        }

        class WargearOptionHolder extends RecyclerView.ViewHolder {
            public Spinner spinner;

            public WargearOptionHolder(View itemView) {
                super(itemView);
                spinner = itemView.findViewById(R.id.wargear_option_spinner);
            }
        }
    }

    public class WargearOptionSpinner implements AdapterView.OnItemSelectedListener {

        Model model;
        WargearOption wargearOption;
        int prevPos;
        WargearOptionSpinner(Model model, WargearOption wargearOption, int prevPos) {
            this.model = model;
            this.wargearOption = wargearOption;
            this.prevPos = prevPos;
        }
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            if (prevPos != i) {
                Toast.makeText(view.getContext(), "Swap "+ adapterView.getAdapter().getItem(prevPos) +" with " +adapterView.getAdapter().getItem(i).toString(), Toast.LENGTH_SHORT).show();
                unit.mutateModel(model, wargearOption, i);
                ModelCompositionAdapter.this.notifyDataSetChanged();
                prevPos = i;
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }


    }
}
