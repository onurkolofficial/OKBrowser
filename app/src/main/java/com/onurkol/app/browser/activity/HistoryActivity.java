package com.onurkol.app.browser.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.onurkol.app.browser.R;
import com.onurkol.app.browser.classes.HistoryDaysListAdapter;
import com.onurkol.app.browser.data.HistoryDaysData;
import com.onurkol.app.browser.data.PreferenceData;

import java.util.ArrayList;

import static com.onurkol.app.browser.classes.HistoryController.initHistoryDaysData;
import static com.onurkol.app.browser.classes.Main.setActivity;
import static com.onurkol.app.browser.classes.Main.setContext;
import static com.onurkol.app.browser.data.BrowserData.BROWSER_HISTORY_DAYS;
import static com.onurkol.app.browser.data.PreferenceData.setPreferenceString;

public class HistoryActivity extends AppCompatActivity {

    ImageButton backButton;
    Button deleteAllHistoryButton;
    TextView settingName;
    ListView historyDayList;

    // Adapters
    public HistoryDaysListAdapter historyDaysListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set Context
        setActivity(this); setContext(this);
        // Create
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Get Elements
        backButton=findViewById(R.id.backSettingsButton);
        settingName=findViewById(R.id.settingName);
        historyDayList=findViewById(R.id.historyDayList);
        deleteAllHistoryButton=findViewById(R.id.deleteAllHistory);

        // Set Toolbar Title
        settingName.setText(getString(R.string.history_text));

        // Button Click Events
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Close This Activity
                finish();
            }
        });
        deleteAllHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(HistoryActivity.this);
                builder.setMessage(getString(R.string.sure_history_all_delete))
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(getString(R.string.yes_text), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Reset Preferences
                                setPreferenceString(PreferenceData.getPreferences(),"BROWSER_HISTORY_DAYS","NULL");
                                setPreferenceString(PreferenceData.getPreferences(),"BROWSER_HISTORY_SITES","NULL");
                                // Remove View
                                historyDayList.removeAllViewsInLayout();
                                historyDayList.postInvalidate();
                            }
                        })
                        .setNegativeButton(getString(R.string.no_text), null)
                        .show();
            }
        });

        // Init List Adapter
        initializeHistoryDaysList();
        initHistoryDaysData();
    }

    public void initializeHistoryDaysList(){
        BROWSER_HISTORY_DAYS=new ArrayList<HistoryDaysData>();
        historyDaysListAdapter=new HistoryDaysListAdapter(this,historyDayList,BROWSER_HISTORY_DAYS);
        historyDayList.setAdapter(historyDaysListAdapter);
    }
}