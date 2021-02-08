package com.onurkol.app.browser.classes;

import android.util.Log;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.onurkol.app.browser.data.HistoryData;
import com.onurkol.app.browser.data.HistoryDaysData;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.onurkol.app.browser.classes.OKRandom.randAll;
import static com.onurkol.app.browser.classes.OKRandom.randString;
import static com.onurkol.app.browser.data.BrowserData.BROWSER_HISTORY_DAYS;
import static com.onurkol.app.browser.data.BrowserData.BROWSER_HISTORY_SITES;
import static com.onurkol.app.browser.data.PreferenceData.getPreferenceString;
import static com.onurkol.app.browser.data.PreferenceData.getPreferences;
import static com.onurkol.app.browser.data.PreferenceData.setPreferenceString;

public class HistoryController {
    // Gson
    static Gson gson=new Gson();

    public static void addHistory(String title, String url, String icon){
        // Create Id (for delete data)
        String historyId=randString(10);
        // Add new History
        HistoryData getHistoryData=new HistoryData(title,url,"",getCurrentDate(),historyId);
        // Convert Data
        String getData="["+gson.toJson(getHistoryData)+"]";
        String oldData = getPreferenceString(getPreferences(), "BROWSER_HISTORY_SITES");
        String mergeData="";
        String newData=getData;
        // Check Data
        if(!oldData.equals("NULL")){
            // Remove array sembol '[' & ']'
            getData=getData.substring(1,(getData.length()-1));
            oldData=oldData.substring(1,(oldData.length()-1));
            // Add new Data
            mergeData=getData+","+oldData;
            // Add Array symbol '[{data},{old}]'
            newData="["+mergeData+"]";
        }
        else{
            // Add Data
            BROWSER_HISTORY_SITES.add(getHistoryData);
        }
        // Save Preference
        setPreferenceString(getPreferences(), "BROWSER_HISTORY_SITES", newData);
    }

    public static void addHistoryDays(){
        // Add new History Category
        HistoryDaysData getHistoryDaysData=new HistoryDaysData(getCurrentDate());
        // Convert Data
        String getData="["+gson.toJson(getHistoryDaysData)+"]";
        String oldData = getPreferenceString(getPreferences(), "BROWSER_HISTORY_DAYS");
        String mergeData="";
        String newData=getData;
        // Check Data
        if(!oldData.equals("NULL")){
            // Remove array sembol '[' & ']'
            getData=getData.substring(1,(getData.length()-1));
            oldData=oldData.substring(1,(oldData.length()-1));
            // Add new Data
            mergeData=getData+","+oldData;
            // Add Array symbol '[{data},{old}]'
            newData="["+mergeData+"]";
        }
        else{
            // Add View (for First View)
            BROWSER_HISTORY_DAYS.add(getHistoryDaysData);
        }
        // Save Preference
        setPreferenceString(getPreferences(), "BROWSER_HISTORY_DAYS", newData);
    }

    public static void initHistoryData(LinearLayout viewLayout, HistoryListAdapter adapter, String whichDate){
        // Get Preference Data
        String histories=getPreferenceString(getPreferences(), "BROWSER_HISTORY_SITES");

        if(histories.equals("NULL")){
            // Remove Days -Because history have not.
            BROWSER_HISTORY_DAYS.clear();

        }
        else{
            // Get Preference Data (String to List)
            List<HistoryData> hListData=gson.fromJson(histories, new TypeToken<ArrayList<HistoryData>>(){}.getType());
            // Check Data
            int i=0,count=0;
            while(i<hListData.size()){
                // Get Datas
                String title=hListData.get(i).getHistoryTitle();
                String url=hListData.get(i).getHistoryUrl();
                String days=hListData.get(i).getHistoryDate();
                String historyId=hListData.get(i).getHistoryId();
                // Check; if new day to add view
                if(days.equals(whichDate)){
                    // Add Data
                    BROWSER_HISTORY_SITES.add(new HistoryData(title,url,"",days,historyId));
                    // Add View
                    viewLayout.addView(adapter.getView(count, null, viewLayout));
                    // Count
                    count++;
                }
                i++;
            }
        }
    }

    public static void initHistoryDaysData(){
        // Get Preference Data
        String historyDays=getPreferenceString(getPreferences(), "BROWSER_HISTORY_DAYS");

        if(historyDays.equals("NULL")){
            addHistoryDays();
        }
        else{
            // Get Preference Data (String to List)
            List<HistoryDaysData> hData=gson.fromJson(historyDays, new TypeToken<ArrayList<HistoryDaysData>>(){}.getType());
            // Check Data
            int i=0;
            while(i<hData.size()){
                // Get Data
                String days=hData.get(i).getHistoryDays();
                // Check; if new day to add new date
                if(!days.equals(getCurrentDate()))
                    addHistoryDays();
                else
                    break;
                i++;
            }
            // Get Updated Preference Data
            String updateHistoryDays=getPreferenceString(getPreferences(), "BROWSER_HISTORY_DAYS");
            List<HistoryDaysData> updateHData=gson.fromJson(updateHistoryDays, new TypeToken<ArrayList<HistoryDaysData>>(){}.getType());
            // Add Views.
            int vcount=0;
            while(vcount<updateHData.size()){
                // Get Data
                String days=updateHData.get(vcount).getHistoryDays();
                // Add View
                BROWSER_HISTORY_DAYS.add(new HistoryDaysData(days));
                // Count
                vcount++;
            }
        }
    }

    public static String getCurrentDate(){
        // Get Date
        Date currentDate = Calendar.getInstance().getTime();
        // Convert Format
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return df.format(currentDate);
    }
}
