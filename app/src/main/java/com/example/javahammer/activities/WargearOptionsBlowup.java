package com.example.javahammer.activities;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.javahammer.data.Model;
import com.example.javahammer.R;
import com.example.javahammer.data.Unit;
import com.example.javahammer.data.Wargear;
import com.example.javahammer.data.WargearOption;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class WargearOptionsBlowup extends AppCompatActivity {
    RecyclerView rv;
    Unit unit;
    Model model;
    Button doneBtn;
    ArrayList<WargearOption> wargearOptionArrayList;
    ModelWargearOptionAdapter modelWargearOptionAdapter;
    TextView modelEqiupment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wargear_options_blowup);

        unit = getIntent().getParcelableExtra("Unit");
        Log.v("UNIT PRINT", "\nwargearOptionsBlowup RECEIVING: \n\n" +unit + "\n\n");
        model = getIntent().getParcelableExtra("Model");
        Log.v("reknit()", String.format("before: %s", model));
        for (Model existingModel : unit.getUnitModelComposition().keySet()) {
            if (model.equals(existingModel)) {
                model = existingModel;
            }
        }
        Log.v("WargearOptionsBlowup", String.format("Model: %s", model));


        modelEqiupment = findViewById(R.id.model_equipment_tv);
        doneBtn = findViewById(R.id.done_btn);
        setModelEquipment();
        wargearOptionArrayList = new ArrayList<>(unit.getWargearOptions().stream().filter(n -> n.getRequiredModel().equals("") ||  n.getRequiredModel().equals(model.getName())).collect(Collectors.toList()));
        rv = findViewById(R.id.model_wargear_option_rv);

        modelWargearOptionAdapter = new ModelWargearOptionAdapter();
        rv.setAdapter(modelWargearOptionAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                ModelComposition mc = unit.unitComposition.stream().filter(n -> n.getTemplateModel().getName().equals(model.getName())).findAny().get();
                mc.removeModel(model, 1);
                mc.addModel(model, 1);
                 */
                Intent returnIntent = new Intent();
                Log.v("UNIT PRINT", "\nwargearOptionsBlowup SENDING: \n\n" +unit + "\n\n");
                returnIntent.putExtra("Unit", (Parcelable) unit);
                setResult(AppCompatActivity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }
    public void setModelEquipment() {
        modelEqiupment.setText(model.getWargear().toString());
        modelWargearOptionAdapter = new ModelWargearOptionAdapter();
    }
    public class ModelWargearOptionAdapter extends RecyclerView.Adapter<ModelWargearOptionAdapter.ViewHolder> {

        // FIELDS
        private Context context;

        // CONSTRUCTOR
        public ModelWargearOptionAdapter() {

        }

        // METHODS
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            // Inflate the custom layout
            View contactView = inflater.inflate(R.layout.item_model_wargear_option, parent, false);
            // Return a new holder instance
            ViewHolder viewHolder = new ViewHolder(contactView);
            return viewHolder;
        }
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            WargearOption wargearOption = wargearOptionArrayList.get(position);
            holder.exchangeableWargear.removeAllViews();
            // Determines whether wargearOption can be applied to the model with current wargear selections

            // Binds holder's TextView fields with the corresponding weapons stats
            holder.capacity.setText(wargearOption.getNumActive(unit) +"/" +wargearOption.getCapacity(unit.getNumberModels()));

           // NESTED ADAPTER IDEA
            /*

            class ExchangeableWargearAdapter extends RecyclerView.Adapter<ExchangeableWargearAdapter.ViewHolder> {

                public ExchangeableWargearAdapter() {
                }
                @NonNull
                @Override
                public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    context = parent.getContext();
                    LayoutInflater inflater = LayoutInflater.from(context);
                    View contactView = inflater.inflate(R.layout.item_wargear_option_choice, parent, false);
                    return new ViewHolder(contactView);
                }

                @Override
                public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


                    // Default Wargear case
                    if (getItemViewType(position) == 0) {
                        holder.tv.setText("" +wargearOption.getDefaultWargear().get(0));
                    } else {
                        holder.tv.setText("" +wargearOption.getExchangeableWargear().get(position - 1));
                    }

                }
                @Override
                public int getItemViewType(int position) {
                    if (position == 0) {
                        return 0;
                    } else {
                        return 1;
                    }
                }
                @Override
                public int getItemCount() {
                    return wargearOption.getExchangeableWargear().size() + 1;
                }

                class ViewHolder extends RecyclerView.ViewHolder {
                    public TextView tv;

                    public ViewHolder(View itemView) {
                        super(itemView);
                        tv = itemView.findViewById(R.id.wargear_option_choice_tv);
                    }
                }
            }

            holder.rv.setAdapter(new ExchangeableWargearAdapter());
            holder.rv.setLayoutManager(new LinearLayoutManager(context));
            */

            // RADIOGROUP IDEA

            // Initialize default Wargear RadioButton
            RadioButton radioButton = new RadioButton(context);
            radioButton.setId(0);
            holder.exchangeableWargear.addView(radioButton);
            position = 1;

            for (ArrayList<Wargear> wargear : wargearOption.getExchangeableWargear()) {
                radioButton = new RadioButton(context);
                radioButton.setText(wargear.toString());
                radioButton.setId(position);
                holder.exchangeableWargear.addView(radioButton);
                radioButton.setEnabled(wargearOption.getNumActive(unit) < wargearOption.getCapacity(unit.getNumberModels()));
                position++;

            }
            /*
            for (Map.Entry<Model, Integer> wargearOption.activeModels) {

            }

             */

            int checkedPos = 1 + wargearOption.activeModels.getOrDefault(model, -1);

            Log.v("reknit()", wargearOption.toString());

            RadioButton rb = (RadioButton) holder.exchangeableWargear.findViewById(checkedPos);
            rb.setChecked(true);

            holder.exchangeableWargear.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    Log.v("UNIT PRINT", "\nonCheckedChanged Start: \n\n" +unit + "\n\n");

                    if (i == 0) {
                        Toast.makeText(context, "Freeing up Wargear Option", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Using Wargear Option", Toast.LENGTH_SHORT).show();

                    }
                    model = unit.mutateModel(model, wargearOption, i-1);
                    Log.v("Thomas", String.format("mutated model: %s",model));
                    setModelEquipment();
                    holder.capacity.setText(wargearOption.getNumActive(unit) + "/" +wargearOption.getCapacity(unit.getNumberModels()));
                    if (i == 0 && wargearOption.getNumActive(unit) >= wargearOption.getCapacity(unit.getNumberModels())) {
                        // Deactivates radiogroup buttons that are not the default option
                        for (int position = 1; position < wargearOption.getExchangeableWargear().size() + 1; position++) {
                            radioGroup.findViewById(position).setEnabled(false);
                        }
                    } else {
                        for (int position = 1; position < wargearOption.getExchangeableWargear().size() + 1; position++) {
                            radioGroup.findViewById(position).setEnabled(true);
                        }
                    }
                    Log.v("UNIT PRINT", "\nonCheckedChanged End: \n\n" +unit + "\n\n");
                }
            });

        }
        @Override
        public int getItemCount() {
            return wargearOptionArrayList.size();
        }
        public class ViewHolder extends RecyclerView.ViewHolder {

            public RecyclerView rv;
            public RadioGroup exchangeableWargear;
            public TextView capacity;

            public ViewHolder(View itemView) {
                super(itemView);
                rv = itemView.findViewById(R.id.wargear_option_choices_rv);
                exchangeableWargear = itemView.findViewById(R.id.radio_group);
                capacity = itemView.findViewById(R.id.wargear_option_capacity_tv);
            }
        }
    }

}