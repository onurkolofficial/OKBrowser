package com.onurkol.app.browser.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.onurkol.app.browser.R;
import com.onurkol.app.browser.activity.settings.AboutActivity;
import com.onurkol.app.browser.activity.settings.WebSettingsActivity;

import static com.onurkol.app.browser.data.BrowserData.SEARCH_ENGINES;
import static com.onurkol.app.browser.data.BrowserData.setupSearchEngines;
import static com.onurkol.app.browser.data.PreferenceData.getPreferenceInteger;
import static com.onurkol.app.browser.data.PreferenceData.getPreferences;
import static com.onurkol.app.browser.data.PreferenceData.setPreferenceInteger;

public class SettingsFragment extends PreferenceFragmentCompat {

    Preference webSettings,aboutPreference;
    ListPreference searchEnginePref;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Set Resource
        setPreferencesFromResource(R.xml.setting_preferences,rootKey);

        // Get Preferences
        webSettings=findPreference("show_web_settings");
        aboutPreference=findPreference("show_about");
        searchEnginePref=findPreference("show_search_engines");


        // Check Search Engines
        if(SEARCH_ENGINES.size()<=0)
            setupSearchEngines();

        // Add Search Engines
        CharSequence[] entries=new String[SEARCH_ENGINES.size()];
        CharSequence[] entryValues=new String[SEARCH_ENGINES.size()];

        for(int i=0; i<SEARCH_ENGINES.size(); i++){
            entries[i]=SEARCH_ENGINES.get(i);
            entryValues[i]=String.valueOf(i);
        }
        searchEnginePref.setEntries(entries);
        searchEnginePref.setEntryValues(entryValues);
        searchEnginePref.setValue(String.valueOf(getPreferenceInteger(getPreferences(), "BROWSER_SEARCH_ENGINE")));

        // Preference Click Events
        webSettings.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                // Start Web Setting Activity
                startActivity(new Intent(getActivity(), WebSettingsActivity.class));
                return false;
            }
        });
        aboutPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                // Start Web Setting Activity
                startActivity(new Intent(getActivity(), AboutActivity.class));
                return false;
            }
        });

        // List Preference Change Events
        searchEnginePref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                // Convert Data Integer
                int convertNewValue=Integer.parseInt(newValue.toString());
                // Set Value
                searchEnginePref.setValue(String.valueOf(convertNewValue));
                // Save Preference
                setPreferenceInteger(getPreferences(), "BROWSER_SEARCH_ENGINE",convertNewValue);
                return false;
            }
        });

    }
}