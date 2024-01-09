package com.example.javahammer.activities;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.javahammer.EditRosterSettingsLauncher;
import com.example.javahammer.R;
import com.example.javahammer.data.Roster;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class RosterLibrary extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    ConstraintLayout layout;
    Roster originalRoster;
    Roster rosterExtra;  // Variable used to easily pass an instance of a roster of interest between methods
    ArrayList<Roster> rosterList;
    RecyclerView recyclerView;
    RosterLibraryAdapter rosterLibraryAdapter;

    FloatingActionButton newRosterFab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roster_library);

        // Load in rosters
        rosterList = new ArrayList<>();
        FileInputStream fin;
        ObjectInputStream ois = null;
        try {
            fin = getApplicationContext().openFileInput("Rosters.rosz");
            ois = new ObjectInputStream(fin);
            rosterList = (ArrayList<Roster>) ois.readObject();
            ois.close();
        }
        catch (IOException e) {
            Log.v("RosterLibrary", "No File Found");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        recyclerView = findViewById(R.id.roster_rv);
        newRosterFab = findViewById(R.id.create_new_roster_fab);

        rosterLibraryAdapter = new RosterLibraryAdapter();
        recyclerView.setAdapter(rosterLibraryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        newRosterFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Roster roster = new Roster(null, null, null);
                createNewRosterEditSettings.launch(roster);
              //  modifyRosterLauncher.launch(roster);
            }
        });

        layout = findViewById(R.id.library_layout);
        update();

        if (rosterList.isEmpty()) {
            createPopWindow();
        }

    }

    public void update() {

        // Cleans up RosterList
        for (Roster roster : rosterList) {
            if (roster == null) {
                rosterList.remove(roster);
            }
        }

        rosterLibraryAdapter.notifyDataSetChanged();

        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            fos = openFileOutput("Rosters.rosz", MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(rosterList);

            oos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }


    }

    public void createPopWindow() {
        LayoutInflater inflater = (LayoutInflater)  getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.library_no_rosters_alert, null);

        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;

        boolean focusable = true;
        PopupWindow popupWindow =  new PopupWindow(popupView, width, height, focusable);

        layout.post(new Runnable() {
            @Override
            public void run() {
                popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
            }
        });

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
    public void onItemClick(Roster roster) {
        modifyRosterLauncher.launch(roster);
    }

    ActivityResultLauncher<Roster> createNewRosterEditSettings =
            registerForActivityResult(
                    new EditRosterSettingsLauncher(),
                    new ActivityResultCallback<Roster>() {
                        @Override
                        public void onActivityResult(Roster result) {
                            // Replace parameter roster with new roster
                            modifyRosterLauncher.launch(result);
                        }
                    });

    ActivityResultLauncher<Roster> editRosterSettings =
            registerForActivityResult(
                    new EditRosterSettingsLauncher(),
                    new ActivityResultCallback<Roster>() {
                        @Override
                        public void onActivityResult(Roster result) {

                            if (result != null) {
                                // Replace parameter roster with new roster
                                rosterList.remove(rosterExtra);
                                rosterList.add(result);
                                update();
                            } else {
                                Toast.makeText(RosterLibrary.this, "Nothing", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.edit_popup_option:
                Toast.makeText(this, "EDIT", Toast.LENGTH_LONG).show();
                editRosterSettings.launch(rosterExtra);
                return true;
            case R.id.duplicate_popup_option:
                Toast.makeText(this, "DELETE", Toast.LENGTH_LONG).show();
                rosterList.remove(rosterExtra);
                update();
                return true;
            case R.id.delete_popup_option:
                Toast.makeText(this, "DELETE", Toast.LENGTH_LONG).show();
                rosterList.remove(rosterExtra);
                update();
                return true;
            default:
                Toast.makeText(this, "WHAT!!!", Toast.LENGTH_LONG).show();
                return false;
        }
    }

    public class OpenRoster extends ActivityResultContract<Roster, Roster> {

        @NonNull
        @Override
        public Intent createIntent(@NonNull Context context, Roster roster) {
            originalRoster = roster;
            Intent intent = new Intent(context, RosterBuilder.class);
            intent.putExtra(String.valueOf(R.string.roster_parcelable), (Parcelable) roster);
            return intent;
        }
        @Override
        public Roster parseResult(int resultCode, @Nullable Intent result) {

            if (resultCode != Activity.RESULT_OK) {
                Log.v(this.toString(), "Result not ok");
                return null;
            }

            Roster resultRoster = result.getParcelableExtra(String.valueOf(R.string.roster_parcelable));
            rosterList.remove(originalRoster);
            rosterList.add(resultRoster);
            update();
            return resultRoster;
        }

    }

    ActivityResultLauncher<Roster> modifyRosterLauncher =
            registerForActivityResult(
                    new OpenRoster(),
                    new ActivityResultCallback<Roster>() {
                        @Override
                        public void onActivityResult(Roster result) {
                            // Replace parameter roster with new roster

                        }
                    });
    public class RosterLibraryAdapter extends RecyclerView.Adapter<RosterLibraryAdapter.ViewHolder> {


        public RosterLibraryAdapter() {

        }
        @NonNull
        @Override
        public RosterLibraryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View contactView = inflater.inflate(R.layout.item_roster, parent, false);
            return new RosterLibraryAdapter.ViewHolder(contactView);
        }

        @Override
        public void onBindViewHolder(@NonNull RosterLibraryAdapter.ViewHolder holder, int position) {

            Roster roster = rosterList.get(position);

           // holder.name.setText("" +roster.getName());
            holder.description.setText(
                    String.format("%s\n%s\n%s", roster.getName(), roster.getFaction(), roster.getDetachment())
            );



            holder.points.setText(
                    String.format("%d/\n%d PTS", roster.getPoints(), roster.getBattleSize().getPoints()));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    modifyRosterLauncher.launch(roster);
                }
            });

            holder.ellipsis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rosterExtra = roster;
                    PopupMenu popup = new PopupMenu(RosterLibrary.this, view);
                    popup.setOnMenuItemClickListener(RosterLibrary.this);
                    popup.inflate(R.menu.roster_popup);
                    popup.show();
                }
            });

            holder.imageView.setImageResource(roster.getFaction().getImageRes());
        }
        @Override
        public int getItemCount() {
            return rosterList.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView description;
            TextView points;
            ImageView imageView;
            ImageButton ellipsis;

            public ViewHolder(View itemView) {
                super(itemView);
                description = itemView.findViewById(R.id.item_roster_faction_tv);
                points = itemView.findViewById(R.id.item_roster_faction_points_tv);
                imageView = itemView.findViewById(R.id.item_roster_faction_iv);
                ellipsis = itemView.findViewById(R.id.item_roster_ellipsis_btn);
            }
        }
    }
}