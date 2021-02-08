package com.onurkol.app.browser.data;

import android.content.Context;
import android.content.SharedPreferences;

import static com.onurkol.app.browser.classes.Main.getContext;
import static com.onurkol.app.browser.data.BrowserData.BROWSER_APP_CACHE;
import static com.onurkol.app.browser.data.BrowserData.BROWSER_DESKTOP_MODE;
import static com.onurkol.app.browser.data.BrowserData.BROWSER_GEO_LOCATION;
import static com.onurkol.app.browser.data.BrowserData.BROWSER_INCOGNITO_MODE;
import static com.onurkol.app.browser.data.BrowserData.BROWSER_JAVASCRIPT_MODE;
import static com.onurkol.app.browser.data.BrowserData.BROWSER_LOCAL_STORAGE;
import static com.onurkol.app.browser.data.BrowserData.BROWSER_POPUPS;
import static com.onurkol.app.browser.data.BrowserData.BROWSER_SAVE_FORMS;
import static com.onurkol.app.browser.data.BrowserData.BROWSER_SEARCH_ENGINE;
import static com.onurkol.app.browser.data.BrowserData.BROWSER_ZOOM;
import static com.onurkol.app.browser.data.BrowserData.BROWSER_ZOOM_BUTTONS;

public class PreferenceData {
    private static final String preferenceName="OKBrowserPreference";
    private static final int defaultInteger=-21474199*99;

    public static String getPreferenceString(SharedPreferences preferences, String data_id){
        return preferences.getString(data_id,null);
    }
    public static Boolean getPreferenceBoolean(SharedPreferences preferences, String data_id){
        return preferences.getBoolean(data_id,false);
    }
    public static int getPreferenceInteger(SharedPreferences preferences, String data_id){
        return preferences.getInt(data_id,defaultInteger);
    }
    public static void setPreferenceString(SharedPreferences preferences, String data_id, String data_value){
        preferences.edit().putString(data_id,data_value).apply();
    }
    public static void setPreferenceBoolean(SharedPreferences preferences, String data_id, Boolean data_value){
        preferences.edit().putBoolean(data_id,data_value).apply();
    }
    public static void setPreferenceInteger(SharedPreferences preferences, String data_id, int data_value){
        preferences.edit().putInt(data_id,data_value).apply();
    }
    public static SharedPreferences getPreferences(){
        return getContext().getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
    }

    public static void InitPreferenceData(){
        SharedPreferences prefs=getPreferences();
        // Check Default Values.
        // First Run
        if(getPreferenceString(prefs, "BROWSER_USER_SESSION")==null)
            setPreferenceString(prefs,"BROWSER_USER_SESSION","NULL");
        // Desktop Mode
        if(getPreferenceInteger(prefs, "BROWSER_DESKTOP_MODE")==defaultInteger)
            setPreferenceInteger(prefs,"BROWSER_DESKTOP_MODE",BROWSER_DESKTOP_MODE);
        // Incognito Mode
        if(getPreferenceInteger(prefs, "BROWSER_INCOGNITO_MODE")==defaultInteger)
            setPreferenceInteger(prefs,"BROWSER_INCOGNITO_MODE",BROWSER_INCOGNITO_MODE);
        // Search Engine
        if(getPreferenceInteger(prefs, "BROWSER_SEARCH_ENGINE")==defaultInteger)
            setPreferenceInteger(prefs,"BROWSER_SEARCH_ENGINE",BROWSER_SEARCH_ENGINE);
        // Javascript
        if(getPreferenceInteger(prefs, "BROWSER_JAVASCRIPT")==defaultInteger)
            setPreferenceInteger(prefs,"BROWSER_JAVASCRIPT",BROWSER_JAVASCRIPT_MODE);
        // Geo Location
        if(getPreferenceInteger(prefs, "BROWSER_GEO_LOCATION")==defaultInteger)
            setPreferenceInteger(prefs,"BROWSER_GEO_LOCATION",BROWSER_GEO_LOCATION);
        // Popups
        if(getPreferenceInteger(prefs, "BROWSER_POPUPS")==defaultInteger)
            setPreferenceInteger(prefs,"BROWSER_POPUPS",BROWSER_POPUPS);
        // Local Storage
        if(getPreferenceInteger(prefs, "BROWSER_LOCAL_STORAGE")==defaultInteger)
            setPreferenceInteger(prefs,"BROWSER_LOCAL_STORAGE",BROWSER_LOCAL_STORAGE);
        // Zoom
        if(getPreferenceInteger(prefs, "BROWSER_ZOOM")==defaultInteger)
            setPreferenceInteger(prefs,"BROWSER_ZOOM",BROWSER_ZOOM);
        // Zoom Buttons
        if(getPreferenceInteger(prefs, "BROWSER_ZOOM_BUTTONS")==defaultInteger)
            setPreferenceInteger(prefs,"BROWSER_ZOOM_BUTTONS",BROWSER_ZOOM_BUTTONS);
        // Cache
        if(getPreferenceInteger(prefs, "BROWSER_APP_CACHE")==defaultInteger)
            setPreferenceInteger(prefs,"BROWSER_APP_CACHE",BROWSER_APP_CACHE);
        // Save Forms
        if(getPreferenceInteger(prefs, "BROWSER_SAVE_FORMS")==defaultInteger)
            setPreferenceInteger(prefs,"BROWSER_SAVE_FORMS",BROWSER_SAVE_FORMS);
        // History Days
        if(getPreferenceString(prefs, "BROWSER_HISTORY_DAYS")==null)
            setPreferenceString(prefs,"BROWSER_HISTORY_DAYS","NULL");
        // Histories
        if(getPreferenceString(prefs, "BROWSER_HISTORY_SITES")==null)
            setPreferenceString(prefs,"BROWSER_HISTORY_SITES","NULL");
        // Bookmarks
        if(getPreferenceString(prefs, "BROWSER_BOOKMARKS")==null)
            setPreferenceString(prefs,"BROWSER_BOOKMARKS","NULL");
    }
}
