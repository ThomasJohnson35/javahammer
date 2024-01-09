package com.example.javahammer.archived;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.javahammer.activities.ImportExistingUnit;
import com.example.javahammer.R;
import com.example.javahammer.data.Unit;

public class QuickCalculator extends AppCompatActivity implements QuickCalculatorListener {


    FragmentAttacker attackerFragment;
    FragmentDefender defenderFragment;
    Button attackerFragmentNavBtn;
    Button defenderFragmentNavBtn;
    Button settingsFragmentNavBtn;

    TextView woundsDealt;

    Unit attacker;
    Unit defender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_calculator);

        /*
        Button ImportExistingUnitBtn = findViewById(R.id.importExistingUnitBtn);
        Button QuickAdd = findViewById(R.id.quickAddBtn);
        Button ImportRoster = findViewById(R.id.importRosterBtn);
*/
        //   RecyclerView rvAttacker = (RecyclerView) findViewById(R.id.f)

        /*
        ImportExistingUnitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Quick Calculator", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Void Void = null;
                importExistingUnitsResultLauncher.launch(Void);
             //  switchImportExistingUnitActivities();

            }
            // Log.v("Thomas", "QuickCalculatorBtnPushed");
        });

        */
    //    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        FragmentManager fragmentManager = getSupportFragmentManager();
        Log.v("QuickCalculator onCreate", "attackFragment start");
        attackerFragment = new FragmentAttacker();
        attackerFragment.setListener(this);
        attackerFragmentNavBtn = findViewById(R.id.attackerNavBtn);
        attackerFragmentNavBtn.setOnClickListener(view -> {
            fragmentManager.beginTransaction().hide(defenderFragment).commit();
            fragmentManager.beginTransaction().show(attackerFragment).commit();
        });
        fragmentManager.beginTransaction().add(R.id.fragmentContainerView, attackerFragment).commit();
        Log.v("QuickCalculator onCreate", "attackFragment end");

        Log.v("QuickCalculator onCreate", "defenderFragment start");
        defenderFragment = new FragmentDefender();
        defenderFragment.setListener(this);
        defenderFragmentNavBtn = findViewById(R.id.defenderNavBtn);
        defenderFragmentNavBtn.setOnClickListener(view -> {
            fragmentManager.beginTransaction().hide(attackerFragment).commit();
            fragmentManager.beginTransaction().show(defenderFragment).commit();
        });
        fragmentManager.beginTransaction().add(R.id.fragmentContainerView, defenderFragment).commit();
        Log.v("QuickCalculator onCreate", "defenderFragment end");


        woundsDealt = findViewById(R.id.wounds_dealt);
    }

    public void UpdateDamageDisplay() {

        // Checks if requirements to calculate damage are met
        if(attacker == null || defender == null) {
            return;
        }

        Toast.makeText(this, attacker.getName() +" " +defender.getName(), Toast.LENGTH_LONG).show();


        Log.v("QuickCalculator Update Damage Display",
                "Attacker Info:\n"
                +"Name: " +attacker.getName() +"\n"
         //       +"Weapon " +attacker.getWeapons()
        );

    }

    @Override
    public void SelectAttackerClick(Unit unit) {

        Toast.makeText(this, unit.getName(), Toast.LENGTH_SHORT).show();

        attacker = unit;
        UpdateDamageDisplay();
    }

    @Override
    public void SelectDefenderClick(Unit unit) {
        Toast.makeText(this, unit.getName(), Toast.LENGTH_SHORT).show();

        defender = unit;
        UpdateDamageDisplay();
    }

    @Override
    public void AddAttackerClick() {
        importExistingUnitsResultLauncher.launch(true);
    }

    @Override
    public void AddDefenderClick() {
        importExistingUnitsResultLauncher.launch(false);
    }

    public class PickDatasheets extends ActivityResultContract<Boolean, Pair<Boolean, Unit>> {

        boolean isAttacker;
        @NonNull
        @Override
        public Intent createIntent(@NonNull Context context, Boolean unused) {

            isAttacker = unused;
            Intent intent = new Intent(context, ImportExistingUnit.class);
            //       intent.putExtra("savedDatasheets", new ArrayList<Model>());
            return intent;
        }
        @Override
        public Pair<Boolean, Unit> parseResult(int resultCode, @Nullable Intent result) {


            if (resultCode != Activity.RESULT_OK)
            //        || result == null)
            {
                Log.v("Thomas", "NULL RESULT");
                return null;
            }
            Log.v("Thomas", "RESULT WITH CONTENT");
            Unit d = result.getParcelableExtra("savedDatasheets");

            Log.v("Thomas", "RESULT: " +d.getName());

            return new Pair(isAttacker, d);
        }

    }

    ActivityResultLauncher<Boolean> importExistingUnitsResultLauncher =
            registerForActivityResult(
                    new PickDatasheets(),
                    new ActivityResultCallback<Pair <Boolean, Unit>>() {
                        @Override
                        public void onActivityResult(Pair<Boolean, Unit> result) {

                            Unit unit = result.second;
                            Log.v("QuickCalculator onActivityResult data received" ,
                                    "Name: "+unit.getName() + "\n"   /* +


                            "Movement: "+ unit.getMovement() + "\n" +
                            "Toughness: " + unit.getToughness() + "\n" +
                            "Save: "+ unit.getSave() + "\n" +
                            "Wounds: "+ unit.getWounds() + "\n" +
                            "Leadership : "+ unit.getLeadership() + "\n" +
                            "Objective Control : "+ unit.getObjectiveCharacteristic() + "\n" +
                            "Weapons : "+ unit.getWeapons()
                                */
                            );


                            if(result.first) {
                                attackerFragment.addUnit(unit);
                            } else {
                                defenderFragment.addUnit(unit);
                            }
                        }
                    });
}