package com.onurkol.app.browser.fragments.settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.CheckBoxPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onurkol.app.browser.R;

import static com.onurkol.app.browser.data.PreferenceData.getPreferenceInteger;
import static com.onurkol.app.browser.data.PreferenceData.getPreferences;
import static com.onurkol.app.browser.data.PreferenceData.setPreferenceInteger;

public class WebSettingsFragment extends PreferenceFragmentCompat {

    CheckBoxPreference geoLocationPref,javascriptPref,popupsPref,saveFormsPref,zoomPref,zoomButtonsPref,localStoragePref,appCachePref;
    int geoLocationData,javascriptData,popupsData,saveFormsData,zoomData,zoomButtonsData,localStorageData,appCacheData;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Set Resource
        setPreferencesFromResource(R.xml.setting_web_preferences,rootKey);

        // Get Preferences
        geoLocationPref=findPreference("setting_geo_location");
        javascriptPref=findPreference("setting_javascript");
        popupsPref=findPreference("setting_popups");
        saveFormsPref=findPreference("setting_save_forms");
        zoomPref=findPreference("setting_zoom");
        zoomButtonsPref=findPreference("setting_zoom_buttons");
        localStoragePref=findPreference("setting_local_storage");
        appCachePref=findPreference("setting_app_cache");

        // Get Preference Data
        geoLocationData=getPreferenceInteger(getPreferences(), "BROWSER_GEO_LOCATION");
        javascriptData=getPreferenceInteger(getPreferences(), "BROWSER_JAVASCRIPT");
        popupsData=getPreferenceInteger(getPreferences(), "BROWSER_POPUPS");
        saveFormsData=getPreferenceInteger(getPreferences(), "BROWSER_SAVE_FORMS");
        zoomData=getPreferenceInteger(getPreferences(), "BROWSER_ZOOM");
        zoomButtonsData=getPreferenceInteger(getPreferences(), "BROWSER_ZOOM_BUTTONS");
        localStorageData=getPreferenceInteger(getPreferences(), "BROWSER_LOCAL_STORAGE");
        appCacheData=getPreferenceInteger(getPreferences(), "BROWSER_APP_CACHE");

        // Preference Click Events
        // Geo Location
        geoLocationPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                // Set Value
                geoLocationData=((geoLocationData==1) ? 0 : 1);
                geoLocationPref.setChecked((geoLocationData==1));
                // Save Preference
                setPreferenceInteger(getPreferences(), "BROWSER_GEO_LOCATION", geoLocationData);
                return false;
            }
        });
        // Javascript
        javascriptPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                // Set Value
                javascriptData=((javascriptData==1) ? 0 : 1);
                javascriptPref.setChecked((javascriptData==1));
                // Save Preference
                setPreferenceInteger(getPreferences(), "BROWSER_JAVASCRIPT", javascriptData);
                return false;
            }
        });
        // Popups
        popupsPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                // Set Value
                popupsData=((popupsData==1) ? 0 : 1);
                popupsPref.setChecked((popupsData==1));
                // Save Preference
                setPreferenceInteger(getPreferences(), "BROWSER_POPUPS", popupsData);
                return false;
            }
        });
        // Save Forms
        saveFormsPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                // Set Value
                saveFormsData=((saveFormsData==1) ? 0 : 1);
                saveFormsPref.setChecked((saveFormsData==1));
                // Save Preference
                setPreferenceInteger(getPreferences(), "BROWSER_SAVE_FORMS", saveFormsData);
                return false;
            }
        });
        // Zoom
        zoomPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                // Set Value
                zoomData=((zoomData==1) ? 0 : 1);
                zoomPref.setChecked((zoomData==1));
                zoomButtonsPref.setEnabled((zoomData==1));
                // Save Preference
                setPreferenceInteger(getPreferences(), "BROWSER_ZOOM", zoomData);
                return false;
            }
        });
        // Zoom Buttons
        zoomButtonsPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                // Set Value
                zoomButtonsData=((zoomButtonsData==1) ? 0 : 1);
                zoomButtonsPref.setChecked((zoomButtonsData==1));
                // Save Preference
                setPreferenceInteger(getPreferences(), "BROWSER_ZOOM_BUTTONS", zoomButtonsData);
                return false;
            }
        });
        // Local Storage
        localStoragePref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                // Set Value
                localStorageData=((localStorageData==1) ? 0 : 1);
                localStoragePref.setChecked((localStorageData==1));
                // Save Preference
                setPreferenceInteger(getPreferences(), "BROWSER_LOCAL_STORAGE", localStorageData);
                return false;
            }
        });
        // App Cache
        appCachePref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                // Set Value
                appCacheData=((appCacheData==1) ? 0 : 1);
                appCachePref.setChecked((appCacheData==1));
                // Save Preference
                setPreferenceInteger(getPreferences(), "BROWSER_APP_CACHE", appCacheData);
                return false;
            }
        });
        // Init Settings
        initCheckSettings();
    }


    public void initCheckSettings(){
        geoLocationPref.setChecked(geoLocationData==1);
        javascriptPref.setChecked(javascriptData==1);
        popupsPref.setChecked(popupsData==1);
        saveFormsPref.setChecked(saveFormsData==1);
        zoomPref.setChecked(zoomData==1);
        zoomButtonsPref.setEnabled(zoomData==1);
        zoomButtonsPref.setChecked(zoomButtonsData==1);
        localStoragePref.setChecked(localStorageData==1);
        appCachePref.setChecked(appCacheData==1);
    }
}