package com.onurkol.app.browser.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.onurkol.app.browser.R;
import com.onurkol.app.browser.classes.OKWebView;
import com.onurkol.app.browser.classes.TabListAdapter;
import com.onurkol.app.browser.data.PreferenceData;
import com.onurkol.app.browser.data.TabData;
import com.startapp.sdk.ads.banner.Banner;
import com.startapp.sdk.ads.banner.BannerListener;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.onurkol.app.browser.classes.Main.getContext;
import static com.onurkol.app.browser.classes.Main.getStringDrawable;
import static com.onurkol.app.browser.classes.Main.hideKeyboard;
import static com.onurkol.app.browser.classes.Main.initStoragePermission;
import static com.onurkol.app.browser.classes.Main.setActivity;
import static com.onurkol.app.browser.classes.Main.setBrowserTabsLayout;
import static com.onurkol.app.browser.classes.Main.setContext;
import static com.onurkol.app.browser.classes.Main.setTabListView;
import static com.onurkol.app.browser.classes.MainMenuPopupWindow.getPopup;
import static com.onurkol.app.browser.classes.TabController.getFragmentTabList;
import static com.onurkol.app.browser.classes.TabController.newTab;
import static com.onurkol.app.browser.classes.TabController.setFragmentManager;
import static com.onurkol.app.browser.classes.TabListAdapter.getCurrentTabItemPosition;
import static com.onurkol.app.browser.classes.TabListAdapter.setCurrentTabItemPosition;
import static com.onurkol.app.browser.data.BrowserData.BROWSER_START_NEW_TAB;
import static com.onurkol.app.browser.data.BrowserData.SEARCH_ENGINE_URLS;
import static com.onurkol.app.browser.data.BrowserData.convertURL;
import static com.onurkol.app.browser.data.BrowserData.isURL;
import static com.onurkol.app.browser.data.BrowserData.setupSearchEngines;
import static com.onurkol.app.browser.data.PreferenceData.InitPreferenceData;
import static com.onurkol.app.browser.data.PreferenceData.getPreferenceInteger;
import static com.onurkol.app.browser.data.PreferenceData.getPreferenceString;
import static com.onurkol.app.browser.fragments.WebViewFragment.getWebView;

public class MainActivity extends AppCompatActivity {
    EditText browserSearch;
    ImageButton browserTabs, browserMenu, addNewTabButton;
    LinearLayout browserTabsLayout;
    public static DrawerLayout browserTabsView;
    ListView tabListView;
    Toolbar toolbarMain;
    ProgressBar browserProgress;
    private static WeakReference<ImageButton> browserTabsWeak;
    private static WeakReference<Toolbar> toolbarMainWeak;
    private static WeakReference<Banner> appBannerWeak;

    public static ArrayList<TabData> tabListArray;
    public TabListAdapter tabListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set Context
        setActivity(this); setContext(this);
        // Init Preference Data
        InitPreferenceData();
        // Preference
        SharedPreferences preferences=PreferenceData.getPreferences();
        // Create
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set Fragment Manager
        setFragmentManager(getSupportFragmentManager());

        // Ads Initialize
        String getAppId=getString(R.string.startapp_app_id);
        StartAppSDK.init(this, getAppId, false);
        // Disable Startapp Splash Screen.
        StartAppAd.disableSplash();
        // Get Banner
        final Banner appBanner=findViewById(R.id.startAppBanner);
        appBannerWeak=new WeakReference<Banner>(appBanner);
        // Set Listener
        appBanner.setBannerListener(new BannerListener() {
            @Override
            public void onReceiveAd(View view) {
                appBanner.setVisibility(View.VISIBLE);
            }
            @Override
            public void onFailedToReceiveAd(View view) {
                appBanner.setVisibility(View.GONE);
            }
            @Override
            public void onImpression(View view) {}
            @Override
            public void onClick(View view) {}
        });
        // Hide Default
        appBanner.setVisibility(View.GONE);

        // Get View Elements
        toolbarMain=findViewById(R.id.browserMainToolbar);
        browserMenu=findViewById(R.id.browserMenu);
        browserTabs=findViewById(R.id.browserTabs);
        browserTabsLayout=findViewById(R.id.browserTabsLayout);
        browserTabsView=findViewById(R.id.tabListNavigation);
        tabListView=findViewById(R.id.tabsListView);
        addNewTabButton=findViewById(R.id.addNewTabButton);
        browserSearch=findViewById(R.id.browserSearch);
        browserProgress=findViewById(R.id.browserProgressbar);
        // Set Required Elements
        browserTabsWeak=new WeakReference<ImageButton>(browserTabs);
        toolbarMainWeak=new WeakReference<Toolbar>(toolbarMain);
        setBrowserTabsLayout(browserTabsLayout);
        setTabListView(tabListView);

        // Button Click Events
        // Toolbar Main Menu
        browserMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPopup().showAsDropDown(view);
            }
        });
        // Show Tab List (Navigation Drawer)
        browserTabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open Drawer
                browserTabsView.openDrawer(browserTabsLayout);
            }
        });
        // Add new Tab
        addNewTabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set New Tab Status
                BROWSER_START_NEW_TAB=true;
                // Add Tab Data & Add New Fragment
                newTab();
                // Hide Drawer
                browserTabsView.closeDrawer(browserTabsLayout);
            }
        });
        // Search Web
        browserSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getKeyCode()==KeyEvent.KEYCODE_ENTER){
                    String getUrl=browserSearch.getText().toString();
                    String goUrl="";
                    if(isURL(getUrl)){
                        // Start WebView and Go Url
                        goUrl=convertURL(getUrl);
                    }
                    else{
                        // Start WebView and Go Search Engine
                        // Get Search Engine
                        int searchEngineId=getPreferenceInteger(PreferenceData.getPreferences(), "BROWSER_SEARCH_ENGINE");
                        // Encode Search Text
                        String searchKey=Uri.encode(getUrl);
                        // Build URL
                        goUrl=SEARCH_ENGINE_URLS.get(searchEngineId) + searchKey;
                    }

                    // Get WebView
                    getWebView().loadUrl(goUrl);
                    // Set Web Url to Search
                    browserSearch.setText(getWebView().getUrl());

                    // Hide Keyboard
                    hideKeyboard(browserSearch);
                    // Clear EditText Focus
                    toolbarMain.requestFocus();
                }
                return false;
            }
        });
        // Set Input Type
        browserSearch.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        // Touch and select all text
        browserSearch.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                browserSearch.selectAll();
                return false;
            }
        });

        // Check First Run
        String checkSession=getPreferenceString(preferences, "BROWSER_USER_SESSION");
        if(checkSession.equals("NULL")){
            // Start Welcome Activity
            Intent welcomeActivity=new Intent(getContext(),WelcomeActivity.class);
            startActivity(welcomeActivity);
            finish();
        }
        else {
            // Check Activity Mode
            String getActivityMode = ((getIntent().getExtras()!=null) ? getIntent().getExtras().getString("ACTIVITY_MODE") : null);
            if(getIntent().getExtras()!=null && getActivityMode!=null) {
                if (getActivityMode.equals("OPEN_SHORTCUT_BOOKMARK")) {
                    String getUrl = getIntent().getExtras().getString("BOOKMARK_URL");
                    // Show Tab List
                    initTabData();
                    // Start First Tab
                    newTab(getUrl);
                } else if (getActivityMode.equals("OPEN_SHORTCUT_HISTORY")) {
                    String getUrl = getIntent().getExtras().getString("HISTORY_URL");
                    // Show Tab List
                    initTabData();
                    // Start First Tab
                    newTab(getUrl);
                }
            }
            else {
                // Setup Search Engines
                setupSearchEngines();
                // Show Tab List
                initTabData();
                // Start First Tab
                newTab();
            }
        }
        // Check Storage Permissions
        initStoragePermission();
    }

    public void initTabData(){
        tabListArray=new ArrayList<TabData>();
        tabListAdapter=new TabListAdapter(getContext(),tabListView,tabListArray);
        tabListView.setAdapter(tabListAdapter);
    }

    public static void updateTabCount(){
        // Update Tab Count
        int tabs=getFragmentTabList().size();
        // Update Tab Preview Index
        setCurrentTabItemPosition(tabs-1);
        // Get Drawable
        String drawName;
        if(tabs>9)
            drawName="ic_baseline_filter_9_plus_24";
        else if(tabs>0)
            drawName="ic_baseline_filter_"+tabs+"_24";
        else
            drawName="ic_baseline_filter_1_24";
        browserTabsWeak.get().setImageDrawable(getStringDrawable(drawName));
    }

    public static void showMainToolbar(){
        toolbarMainWeak.get().setVisibility(View.VISIBLE);
        appBannerWeak.get().setVisibility(View.VISIBLE);

    }
    public static void hideMainToolbar(){
        toolbarMainWeak.get().setVisibility(View.GONE);
        appBannerWeak.get().setVisibility(View.GONE);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        // Reset Context
        setActivity(this); setContext(this);
        super.onResume();
        // Get Update Current Position
        setCurrentTabItemPosition(getCurrentTabItemPosition());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        OKWebView webView=getWebView();
        if(webView.canGoBack())
            webView.goBack();
        else
            super.onBackPressed();
    }
}