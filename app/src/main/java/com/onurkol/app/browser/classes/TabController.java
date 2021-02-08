package com.onurkol.app.browser.classes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.onurkol.app.browser.R;
import com.onurkol.app.browser.activity.MainActivity;
import com.onurkol.app.browser.data.TabData;
import com.onurkol.app.browser.fragments.WebViewFragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static com.onurkol.app.browser.activity.MainActivity.showMainToolbar;
import static com.onurkol.app.browser.activity.MainActivity.updateTabCount;
import static com.onurkol.app.browser.classes.Main.getBrowserTabsLayout;
import static com.onurkol.app.browser.classes.Main.getContext;
import static com.onurkol.app.browser.classes.Main.getTabListView;
import static com.onurkol.app.browser.classes.TabListAdapter.getTabData;
import static com.onurkol.app.browser.fragments.WebViewFragment.getWebView;
import static com.onurkol.app.browser.fragments.WebViewFragment.setWebView;
import static com.onurkol.app.browser.data.BrowserData.BROWSER_REFRESH_STATUS;

public class TabController {
    private static final List<Fragment> FragmentTabList=new ArrayList<Fragment>();
    private static WeakReference<Fragment> CurrentTabFragment;

    private static FragmentManager fragmentManager;

    public static void newTab(){
        newTabClass("");
    }
    public static void newTab(String Url){
        newTabClass(Url);
    }
    public static void newTabClass(String url){
        // Fragment Bundle
        Bundle bundle = new Bundle();
        // Put Send Url
        bundle.putString("webViewLoadUrl",url);
        // Get New Tab String
        String newTabString=getContext().getString(R.string.new_tab);
        // Create Tab Fragment
        WebViewFragment webViewFragment=new WebViewFragment();
        // Add View
        getFragmentManager().beginTransaction()
                .add(R.id.browserFragmentView, webViewFragment).commit();
        // Set Bundle
        webViewFragment.setArguments(bundle);
        // Add Fragment to Array
        FragmentTabList.add(webViewFragment);
        // Default Tab Drawable
        Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_baseline_add_box_24);
        // Add Tab
        getTabData().add(new TabData(newTabString,bitmap));
        getTabListView().invalidateViews();
        // Update Tab Counts
        updateTabCount();
        // Update Fragment and WebView
        updateFragments(-1,false); // New Tab
        // Reset Toolbar Status
        showMainToolbar();
    }
    public static void closeTab(int tabPosition){
        // Set Refresh Status
        BROWSER_REFRESH_STATUS = false;
        // Reset Ui
        resetUi(getWebView());
        // Destroy Web View
        OKWebView destroyWebView=FragmentTabList.get(tabPosition).getView().findViewWithTag("browserWebView");
        destroyWebView.destroy();
        // Remove Fragment
        fragmentManager.beginTransaction()
                .remove(FragmentTabList.get(tabPosition)).commit();
        fragmentManager.popBackStack();
        // Destroy Fragment
        FragmentTabList.get(tabPosition).onDestroy();
        // Remove Array
        FragmentTabList.remove(tabPosition);
        // Update Fragment and WebView
        // Check Process Tab
        updateFragments(tabPosition,(FragmentTabList.size()>tabPosition)); // Open Next Or Previous Tab
        // Update Tab Counts
        updateTabCount();
    }
    public static void closeAllTab(){
        // Set Refresh Status
        BROWSER_REFRESH_STATUS = false;
        // Reset Ui
        resetUi(getWebView());
        // Get All Tabs and Fragments List
        int fragList=FragmentTabList.size();
        for (int i = 0; i < fragList; i++) {
            // Remove WebViews
            OKWebView getWV=FragmentTabList.get(i).getView().findViewWithTag("browserWebView");
            getWV.destroy();
            // Remove Fragments
            fragmentManager.beginTransaction().remove(FragmentTabList.get(i)).commit();
        }
        // Remove Arrays
        getTabData().clear();
        FragmentTabList.clear();
        // Update Fragment and WebView
        updateFragments(0,false);
        // Update Tab Counts
        updateTabCount();
    }
    public static void startTab(int tabPosition){
        // Set Refresh Status
        BROWSER_REFRESH_STATUS = false;
        // Get Fragment
        Fragment callFragment=FragmentTabList.get(tabPosition);
        // Set Fragment
        setCurrentTabFragment(callFragment);
        // Set WebView
        setWebView(callFragment.getView().findViewWithTag("browserWebView"));
        // Get Search Input from webview root.
        View rootView=getWebView().getRootView();
        // Get Search Input
        EditText browserSearch=rootView.findViewById(R.id.browserSearch);
        // Reset Ui
        resetUi(getWebView());
        // Replace Web Title
        browserSearch.setText(getWebView().getUrl());
        // Replace Fragments
        int fragList=FragmentTabList.size();
        for (int i = 0; i < fragList; i++) {
            if(FragmentTabList.get(i)==callFragment){
                if(callFragment.isHidden())
                    fragmentManager.beginTransaction().show(callFragment).commit();
            }
            else{
                fragmentManager.beginTransaction().hide(FragmentTabList.get(i)).commit();
            }
        }
    }

    public static void updateFragments(int processTabId, boolean Type){
        // @processTabId -> -1 to 'New Tab'
        // @precessTabId -> @Type -> false to Set and Show Previous tab
        // @precessTabId -> @Type -> true  to Set and Show Next tab

        // Get Fragment List
        int tabs=FragmentTabList.size();
        if(tabs<=0){
            // New Tab
            newTab();
            // Dismiss tab list.
            MainActivity.browserTabsView.closeDrawer(getBrowserTabsLayout());
        }
        else{
            int changeTab;
            if(processTabId==-1){
                changeTab = tabs - 1;
            }
            else{
                if(!Type)
                    changeTab = processTabId - 1;
                else
                    changeTab = processTabId;
                // Set Fragment
                setCurrentTabFragment(FragmentTabList.get(changeTab));
                // Set WebView
                setWebView(FragmentTabList.get(changeTab).getView().findViewWithTag("browserWebView"));
            }
            // Hide all tabs
            for (int i = 0; i < tabs; i++) {
                if (i != changeTab)
                    fragmentManager.beginTransaction().hide(FragmentTabList.get(i)).commit();
                else
                    fragmentManager.beginTransaction().show(FragmentTabList.get(i)).commit();
            }
        }
    }

    public static void resetUi(View webView){
        View rootView=webView.getRootView();
        // Get Root Elements
        ProgressBar browserProgress=rootView.findViewById(R.id.browserProgressbar);
        // Check Progressbar (<Bug> Close tab to stop progressbar)
        if(browserProgress!=null && browserProgress.getProgress()!=0)
            browserProgress.setProgress(0);

        // Reset Toolbar Status
        showMainToolbar();
    }

    public static List<Fragment> getFragmentTabList(){
        return FragmentTabList;
    }

    public static void setCurrentTabFragment(Fragment fragment){
        CurrentTabFragment=new WeakReference<Fragment>(fragment);
    }
    public static Fragment getCurrentTabFragment(){
        return CurrentTabFragment.get();
    }

    public static void setFragmentManager(FragmentManager mFragmentManager){
        fragmentManager=mFragmentManager;
    }
    public static FragmentManager getFragmentManager(){
        return fragmentManager;
    }

}
