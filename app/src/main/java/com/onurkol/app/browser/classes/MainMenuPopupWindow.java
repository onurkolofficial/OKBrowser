package com.onurkol.app.browser.classes;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.onurkol.app.browser.R;
import com.onurkol.app.browser.activity.BookmarksActivity;
import com.onurkol.app.browser.activity.HistoryActivity;
import com.onurkol.app.browser.activity.SettingsActivity;
import com.onurkol.app.browser.data.BookmarksData;

import java.util.ArrayList;
import java.util.List;

import static com.onurkol.app.browser.classes.BookmarkController.addBookmark;
import static com.onurkol.app.browser.classes.BookmarkController.removeBookmark;
import static com.onurkol.app.browser.classes.Main.getActivity;
import static com.onurkol.app.browser.classes.Main.getContext;
import static com.onurkol.app.browser.classes.TabController.closeAllTab;
import static com.onurkol.app.browser.classes.TabController.newTab;
import static com.onurkol.app.browser.data.BrowserData.BROWSER_REFRESH_MODE;
import static com.onurkol.app.browser.data.BrowserData.BROWSER_REFRESH_STATUS;
import static com.onurkol.app.browser.data.BrowserData.BROWSER_START_NEW_TAB;
import static com.onurkol.app.browser.data.BrowserData.DESKTOP_USER_AGENT;
import static com.onurkol.app.browser.data.PreferenceData.getPreferenceInteger;
import static com.onurkol.app.browser.data.PreferenceData.getPreferenceString;
import static com.onurkol.app.browser.data.PreferenceData.getPreferences;
import static com.onurkol.app.browser.data.PreferenceData.setPreferenceInteger;
import static com.onurkol.app.browser.fragments.WebViewFragment.getWebView;

public class MainMenuPopupWindow {
    // Popup Menu Window
    public static PopupWindow getPopup(){
        final PopupWindow popupWindow = new PopupWindow(getActivity());

        ImageButton forwardBtn,addBookmarkBtn,downloadsBtn,historyBtn,refreshBtn;
        Button newTabBtn,closeAllTabBtn,bookmarksBtn,settingsBtn,exitBrowserBtn;
        LinearLayout desktopModeBtn,incognitoModeBtn;
        CheckBox desktopModeCheck,incognitoModeCheck;

        // inflate your layout or dynamically add view
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.menu_toolbar_main, null);

        popupWindow.setFocusable(true);
        popupWindow.setWidth(640);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);
        popupWindow.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getContext(),R.color.popupMenuBackground)));
        popupWindow.setElevation(20);

        // Get View Elements (menu items)
        forwardBtn=view.findViewById(R.id.forwardButton);
        refreshBtn=view.findViewById(R.id.refreshButton);
        addBookmarkBtn=view.findViewById(R.id.addBookmarkButton);
        downloadsBtn=view.findViewById(R.id.downloadsButton);
        historyBtn=view.findViewById(R.id.historyButton);
        newTabBtn=view.findViewById(R.id.newTabButton);
        incognitoModeBtn=view.findViewById(R.id.incognitoModeCBoxClick);
        incognitoModeCheck=view.findViewById(R.id.incognitoModeCBox);
        closeAllTabBtn=view.findViewById(R.id.closeAllTabButton);
        bookmarksBtn=view.findViewById(R.id.bookmarksButton);
        settingsBtn=view.findViewById(R.id.settingsButton);
        exitBrowserBtn=view.findViewById(R.id.exitButton);
        desktopModeBtn=view.findViewById(R.id.desktopModeCBoxClick);
        desktopModeCheck=view.findViewById(R.id.desktopModeCBox);

        // Get WebView
        OKWebView webView=getWebView();

        // Gson
        Gson gson=new Gson();

        // Get Preference Settings
        int desktopMode=getPreferenceInteger(getPreferences(), "BROWSER_DESKTOP_MODE");
        int incognitoMode=getPreferenceInteger(getPreferences(), "BROWSER_INCOGNITO_MODE");
        String bookmarks=getPreferenceString(getPreferences(), "BROWSER_BOOKMARKS");

        newTabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set New Tab Status
                BROWSER_START_NEW_TAB=true;
                // Add Tab Data & Add New Fragment
                newTab();
                // Dismiss Popup
                popupWindow.dismiss();
            }
        });
        closeAllTabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Close All Tabs
                closeAllTab();
                // Dismiss Popup
                popupWindow.dismiss();
            }
        });
        bookmarksBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show Bookmarks
                getContext().startActivity(new Intent(getActivity(), BookmarksActivity.class));
                // Dismiss Popup
                popupWindow.dismiss();
            }
        });
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open Settings
                getContext().startActivity(new Intent(getActivity(), SettingsActivity.class));
                // Dismiss Popup
                popupWindow.dismiss();
            }
        });
        incognitoModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Out Message
                String Message="";
                if(incognitoMode==0) {
                    // Open Incognito Mode
                    // Save Preference
                    setPreferenceInteger(getPreferences(), "BROWSER_INCOGNITO_MODE", 1);
                    // Get Message
                    Message=getContext().getString(R.string.incognito_mode_on);
                }
                else if(incognitoMode==1){
                    // Close Incognito Mode
                    // Save Preference
                    setPreferenceInteger(getPreferences(), "BROWSER_INCOGNITO_MODE", 0);
                    // Get Message
                    Message=getContext().getString(R.string.incognito_mode_off);
                }
                // Show Message
                Toast.makeText(getActivity(), Message, Toast.LENGTH_SHORT).show();
                // Dismiss Popup
                popupWindow.dismiss();
            }
        });
        desktopModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(desktopMode==0){
                    // Open Desktop Mode
                    // Save Preference
                    setPreferenceInteger(getPreferences(), "BROWSER_DESKTOP_MODE",1);
                    // Set User Agent for WebView.
                    getWebView().getSettings().setUserAgentString(DESKTOP_USER_AGENT);
                }
                else{
                    // Close Desktop Mode
                    // Save Preference
                    setPreferenceInteger(getPreferences(), "BROWSER_DESKTOP_MODE",0);
                    // Set User Agent for WebView.
                    getWebView().getSettings().setUserAgentString(null);
                }
                // Refresh Current WebView
                getWebView().reload();
                // Dismiss Popup
                popupWindow.dismiss();
            }
        });
        exitBrowserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
        forwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go Forward.
                webView.goForward();
                // Dismiss Popup
                popupWindow.dismiss();
            }
        });
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set Refresh Mode
                BROWSER_REFRESH_MODE="Refresh";

                if(BROWSER_REFRESH_STATUS) {
                    webView.stopLoading();
                    BROWSER_REFRESH_STATUS=false;
                    // Change refresh icon
                    refreshBtn.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_baseline_refresh_24));
                }
                else {
                    webView.reload();
                    BROWSER_REFRESH_STATUS=true;
                    // Change refresh icon
                    refreshBtn.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_baseline_close_24));
                }
                // Dismiss Popup
                popupWindow.dismiss();
            }
        });
        addBookmarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check Exist Bookmark
                List<BookmarksData> getBookmarksList=gson.fromJson(bookmarks, new TypeToken<ArrayList<BookmarksData>>(){}.getType());
                if(!bookmarks.equals("NULL")) {
                    int i = 0;
                    while (i < getBookmarksList.size()) {
                        // Get Data
                        String bookmarkId = getBookmarksList.get(i).getBookmarkId();
                        String bookmarkTitle = getBookmarksList.get(i).getBookmarkTitle();
                        String bookmarkUrl = getBookmarksList.get(i).getBookmarkUrl();
                        // Get WebView Data
                        String webUrl = webView.getUrl();
                        if (webUrl.equals(bookmarkUrl)) {
                            // Remove Bookmark
                            removeBookmark(bookmarkId, bookmarkTitle, bookmarkUrl);
                            // Show Message
                            Toast.makeText(getActivity(), getActivity().getString(R.string.removed_bookmark), Toast.LENGTH_SHORT).show();
                            break;
                        } else {
                            // Add Bookmark
                            addBookmark(webView.getTitle(), webView.getUrl());
                            // Show Message
                            Toast.makeText(getActivity(), getActivity().getString(R.string.added_bookmark), Toast.LENGTH_SHORT).show();
                        }
                        //Count
                        i++;
                    }
                }
                else{
                    // Add Bookmark
                    addBookmark(webView.getTitle(), webView.getUrl());
                    // Show Message
                    Toast.makeText(getActivity(), getActivity().getString(R.string.added_bookmark), Toast.LENGTH_SHORT).show();
                }
                // Dismiss Popup
                popupWindow.dismiss();
            }
        });
        downloadsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show Downloads
                getContext().startActivity(new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));
                // Dismiss Popup
                popupWindow.dismiss();
            }
        });
        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show History
                getContext().startActivity(new Intent(getActivity(),HistoryActivity.class));
                // Dismiss Popup
                popupWindow.dismiss();
            }
        });

        // Check Checkboxes
        // Desktop Mode
        desktopModeCheck.setChecked(desktopMode != 0);
        // Incognito Mode
        incognitoModeCheck.setChecked(incognitoMode != 0);

        // Check WebView Settings (Forward Button)
        forwardBtn.setEnabled(webView.canGoForward());

        // Check Refresh Status
        if(BROWSER_REFRESH_STATUS)
            refreshBtn.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_baseline_close_24));
        else
            refreshBtn.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_baseline_refresh_24));

        // Check Bookmarks
        if(!bookmarks.equals("NULL")) {
            List<BookmarksData> getBookmarksList = gson.fromJson(bookmarks, new TypeToken<ArrayList<BookmarksData>>() {}.getType());
            int i = 0;
            while (i < getBookmarksList.size()) {
                // Get Data
                String bookmarkUrl = getBookmarksList.get(i).getBookmarkUrl();
                if (webView.getUrl().equals(bookmarkUrl)) {
                    addBookmarkBtn.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_baseline_star_24));
                    break;
                } else {
                    addBookmarkBtn.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_baseline_star_border_24));
                }
                i++;
            }
        }
        else{
            addBookmarkBtn.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_baseline_star_border_24));
        }


        return popupWindow;
    }
}
