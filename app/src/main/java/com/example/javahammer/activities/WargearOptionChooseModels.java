package com.example.javahammer.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.javahammer.R;
import com.example.javahammer.adapters.ModelAdapter;
import com.example.javahammer.data.Model;
import com.example.javahammer.data.Unit;
import com.example.javahammer.data.Wargear;
import com.example.javahammer.data.WargearOption;
import com.example.javahammer.interfaces.AdapterListener;

import java.util.ArrayList;
import java.util.HashMap;

public class WargearOptionChooseModels extends AppCompatActivity implements AdapterListener {

    Unit unit;
    WargearOption wargearOption;
    TextView wargearOptionCountTv;
    ModelAdapter modelAdapter;
    RecyclerView modelRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wargear_option_choose_models);
        unit = getIntent().getParcelableExtra("Unit");
        wargearOption = getIntent().getParcelableExtra("WargearOption");

        // TODO FIX
        modelAdapter = new ModelAdapter(unit.getUnitComposition());
        //
        modelRv = findViewById(R.id.wargear_option_choose_model_rv);
        modelRv.setAdapter(modelAdapter);
        modelRv.setLayoutManager(new LinearLayoutManager(this));

        wargearOptionCountTv = findViewById(R.id.wargear_option_active_tv);
        updateWargearOptionCountTv();
    }
    @Override
    public ArrayList<Wargear> getExchangeableWargear() {
        return wargearOption.getExchangeableWargear().get(0);
    }


    // TODO FIX THIS
    @Override
    public HashMap<Model, Integer> activateWargearOption(Model model) {
        unit.mutateModel(model, wargearOption, 0);
        updateWargearOptionCountTv();
        return unit.getUnitModelComposition();
    }

    @Override
    public HashMap<Model, Integer> deactivateWargearOption(Model model) {
        unit.mutateModel(model, wargearOption, 0);
        updateWargearOptionCountTv();
        return unit.getUnitModelComposition();

    }

    public void updateWargearOptionCountTv() {
        wargearOptionCountTv.setText("" +wargearOption.getNumActive(unit));
    }
}