package com.onurkol.app.browser.activity.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.onurkol.app.browser.BuildConfig;
import com.onurkol.app.browser.R;

import static com.onurkol.app.browser.classes.Main.getActivity;
import static com.onurkol.app.browser.data.BrowserData.BROWSER_LICENSE;
import static com.onurkol.app.browser.fragments.WebViewFragment.getWebView;

public class AboutActivity extends AppCompatActivity {

    ImageButton backButton;
    TextView settingName,browserVersion,browserLicense;
    Button webButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Create
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // Get Elements
        backButton=findViewById(R.id.backSettingsButton);
        settingName=findViewById(R.id.settingName);
        browserVersion=findViewById(R.id.browserVersion);
        webButton=findViewById(R.id.webButton);
        browserLicense=findViewById(R.id.browserLicense);

        // Get Version
        String appVersion = BuildConfig.VERSION_NAME+"-"+BuildConfig.VERSION_CODE+" "+BuildConfig.BUILD_TYPE+"";
        // Get License
        String license=((BROWSER_LICENSE==0) ? "free" : "pro");

        // Set Toolbar Title
        settingName.setText(getString(R.string.about_text));
        browserVersion.setText(appVersion);
        browserLicense.setText(license);

        // Button Click Events
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Close This Activity
                finish();
            }
        });

        webButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Load Web Page
                getWebView().loadUrl("https://onurkolofficial.cf");
                // Close Settings Activity
                getActivity().finish();
                // Close This Activity
                finish();
            }
        });
    }
}