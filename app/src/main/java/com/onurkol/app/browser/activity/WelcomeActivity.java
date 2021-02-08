package com.onurkol.app.browser.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.onurkol.app.browser.R;
import com.onurkol.app.browser.data.PreferenceData;

import static com.onurkol.app.browser.classes.Main.setActivity;
import static com.onurkol.app.browser.classes.Main.setContext;
import static com.onurkol.app.browser.classes.OKRandom.randAll;
import static com.onurkol.app.browser.data.PreferenceData.setPreferenceString;

public class WelcomeActivity extends AppCompatActivity {
    ImageButton welcomeNextButton;
    TextView welcomeNextButtonText;
    public static boolean BROWSER_FIRST_RUN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set Context
        setActivity(this); setContext(this);
        // Preference
        SharedPreferences preferences=PreferenceData.getPreferences();
        // Create
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        // Set First Run
        BROWSER_FIRST_RUN=true;

        // Get Elements
        welcomeNextButton=findViewById(R.id.startButton);
        welcomeNextButtonText=findViewById(R.id.startButtonText);

        welcomeNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set Preference Session
                String newSession=randAll(10);
                setPreferenceString(preferences,"BROWSER_USER_SESSION",newSession);
                // Start Browser
                Intent mainActivity=new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(mainActivity);
                finish();
            }
        });
        // Disabled Start Button.
        welcomeNextButton.setEnabled(false);
    }

    // Checkbox Event
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked=((CheckBox)view).isChecked();

        // Check which checkbox was clicked
        if (view.getId() == R.id.licenseCheck) {
            if (checked){
                welcomeNextButton.setEnabled(true);
                welcomeNextButtonText.setTextColor(ContextCompat.getColor(this,R.color.textColorDark));
            }
            else{
                welcomeNextButton.setEnabled(false);
                welcomeNextButtonText.setTextColor(ContextCompat.getColor(this,R.color.textColorLight));
            }
        }
    }
}