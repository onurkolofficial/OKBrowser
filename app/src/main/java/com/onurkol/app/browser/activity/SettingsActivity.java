package com.onurkol.app.browser.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.onurkol.app.browser.R;
import com.onurkol.app.browser.fragments.SettingsFragment;

import static com.onurkol.app.browser.classes.Main.setActivity;
import static com.onurkol.app.browser.classes.Main.setContext;

public class SettingsActivity extends AppCompatActivity {

    ImageButton backButton;
    TextView settingName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set Context
        setActivity(this); setContext(this);
        // Create
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Get Elements
        backButton=findViewById(R.id.backSettingsButton);
        settingName=findViewById(R.id.settingName);

        // Set Toolbar Title
        settingName.setText(getString(R.string.settings));

        // Button Click Events
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Close This Activity
                finish();
            }
        });

        // Get Fragment
        getSupportFragmentManager().beginTransaction().add(R.id.settingsFragmentContent,new SettingsFragment()).commit();
    }
}