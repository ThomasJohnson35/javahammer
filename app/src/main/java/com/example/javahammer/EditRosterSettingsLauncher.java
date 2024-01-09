package com.example.javahammer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.javahammer.activities.EditRosterSettings;
import com.example.javahammer.data.Roster;

public class EditRosterSettingsLauncher extends ActivityResultContract<Roster, Roster> {
    Roster originalRoster;

    @NonNull
    @Override
    public Intent createIntent(@NonNull Context context, Roster roster) {
        Intent intent = new Intent(context, EditRosterSettings.class);
        intent.putExtra(String.valueOf(R.string.roster_parcelable), (Parcelable) roster);
        return intent;
    }
    @Override
    public Roster parseResult(int resultCode, @Nullable Intent result) {

        if (resultCode != Activity.RESULT_OK) {
            Log.v(this.toString(), "Result not ok");
            return null;
        }
        Log.v(this.toString(), "Result ok");
        Roster resultRoster = result.getParcelableExtra(String.valueOf(R.string.roster_parcelable));
        return resultRoster;
    }
}