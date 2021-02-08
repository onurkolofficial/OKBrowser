package com.onurkol.app.browser.classes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.onurkol.app.browser.R;
import com.onurkol.app.browser.activity.MainActivity;
import com.onurkol.app.browser.data.HistoryData;

import java.util.List;

import static com.onurkol.app.browser.classes.Main.getActivity;
import static com.onurkol.app.browser.data.BrowserData.BROWSER_REFRESH_MODE;
import static com.onurkol.app.browser.data.PreferenceData.getPreferenceString;
import static com.onurkol.app.browser.data.PreferenceData.getPreferences;
import static com.onurkol.app.browser.data.PreferenceData.setPreferenceString;
import static com.onurkol.app.browser.fragments.WebViewFragment.getWebView;

public class HistoryListAdapter extends ArrayAdapter<HistoryData> {
    private final LayoutInflater inflater;
    private ViewHolder holder;
    private static List<HistoryData> mHistoryData;
    private final LinearLayout mHistoryListLayout;

    static Gson gson=new Gson();

    public HistoryListAdapter(Context context, LinearLayout historyListLayout, List<HistoryData> historyData) {
        super(context, 0, historyData);
        mHistoryData=historyData;
        mHistoryListLayout=historyListLayout;
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView==null){
            convertView=inflater.inflate(R.layout.history_items, null);
            holder=new ViewHolder();
            holder.siteIcon=convertView.findViewById(R.id.historySiteIcon);
            holder.siteTitle=convertView.findViewById(R.id.historySiteTitle);
            holder.siteUrl=convertView.findViewById(R.id.historyUrl);
            holder.openHistoryButton=convertView.findViewById(R.id.openHistoryButton);
            holder.deleteButton=convertView.findViewById(R.id.historyDeleteButton);
            convertView.setTag(holder);
        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }

        // Get Data
        final HistoryData historyData=mHistoryData.get(position);
        // Write Data
        // Url Character Limit
        String url=historyData.getHistoryUrl();
        if(url.length()>40)
            url=historyData.getHistoryUrl().substring(0,40)+"..."; // Max. 40 char.
        // Title Character Limit
        String title=historyData.getHistoryTitle();
        if(title.length()>22)
            title=historyData.getHistoryUrl().substring(0,22)+"..."; // Max. 25 char.

        holder.siteTitle.setText(title);
        holder.siteUrl.setText(url);

        View getConvertView=convertView;
        // Button Click Events
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Convert Deleted Data to String
                String deleteData=gson.toJson(historyData);
                // Get All Data
                String allData=getPreferenceString(getPreferences(), "BROWSER_HISTORY_SITES");
                // Delete Current Data
                if(mHistoryData.size()>1) {
                    if(position==(mHistoryData.size()-1))
                        deleteData = "," + deleteData;  // ,{DATA} ]end
                    else
                        deleteData += ",";          // {DATA}, {DATA}
                }
                // Out New Data
                String newData=allData.replace(deleteData,"");
                // Save Preference
                setPreferenceString(getPreferences(), "BROWSER_HISTORY_SITES",newData);
                // Delete LinearLayout View.
                mHistoryListLayout.removeView(getConvertView);
            }
        });
        holder.openHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set Refresh Mode
                BROWSER_REFRESH_MODE="Refresh"; // History dont save.

                // Check Open from Shortcut
                if(getWebView()==null){
                    // Get Activity
                    Activity $this=getActivity();
                    // Create new Bundle
                    Bundle bundle = new Bundle();
                    // Set bundle Datas
                    bundle.putString("ACTIVITY_MODE","OPEN_SHORTCUT_HISTORY");
                    bundle.putString("HISTORY_URL",historyData.getHistoryUrl());
                    // Create Main Activity
                    Intent mainActivity=new Intent($this, MainActivity.class);
                    // Put Intent
                    mainActivity.putExtras(bundle);
                    $this.startActivity(mainActivity);
                    // Close this activity
                    $this.finish();
                }
                else {
                    // Load Web Page
                    getWebView().loadUrl(historyData.getHistoryUrl());
                    // Finish Activity
                    getActivity().finish();
                }
            }
        });
        return convertView;
    }

    //View Holder
    private static class ViewHolder {
        ImageView siteIcon;
        TextView siteTitle,siteUrl;
        ImageButton deleteButton;
        LinearLayout openHistoryButton;
    }
}
