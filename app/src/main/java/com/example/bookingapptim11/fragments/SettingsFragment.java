package com.example.bookingapptim11.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.example.bookingapptim11.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    private PreferenceManager prefMgr;
    public SettingsFragment() {
    }
    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        prefMgr = getPreferenceManager();
        prefMgr.setSharedPreferencesName(getString(R.string.pref_file));
        prefMgr.setSharedPreferencesMode(MODE_PRIVATE);
    }
}
