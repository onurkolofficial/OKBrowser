package com.onurkol.app.browser.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.URLUtil;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.onurkol.app.browser.R;
import com.onurkol.app.browser.classes.OKWebView;
import com.onurkol.app.browser.classes.OKWebViewChromeClient;
import com.onurkol.app.browser.classes.OKWebViewClient;
import com.onurkol.app.browser.classes.OKWebViewDownloadListener;

import static com.onurkol.app.browser.classes.TabController.setCurrentTabFragment;
import static com.onurkol.app.browser.classes.WebContextMenu.getAnchorContextMenu;
import static com.onurkol.app.browser.classes.WebContextMenu.getImageAnchorContextMenu;
import static com.onurkol.app.browser.classes.WebContextMenu.getImageContextMenu;
import static com.onurkol.app.browser.data.BrowserData.BROWSER_REFRESH_MODE;
import static com.onurkol.app.browser.data.BrowserData.BROWSER_SEARCH_ENGINE;
import static com.onurkol.app.browser.data.BrowserData.DESKTOP_USER_AGENT;
import static com.onurkol.app.browser.data.BrowserData.SEARCH_ENGINE_HOME_URLS;
import static com.onurkol.app.browser.data.PreferenceData.getPreferenceInteger;
import static com.onurkol.app.browser.data.PreferenceData.getPreferenceString;
import static com.onurkol.app.browser.data.PreferenceData.getPreferences;

public class WebViewFragment extends Fragment {

    private static OKWebView browserWebView;
    LinearLayoutCompat webViewLayout;
    SwipeRefreshLayout browserSwipeRefresh;
    Toolbar toolbarMain;

    static String getTitle,getUrl;

    Handler handler;
    Message message;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_webview, container, false);
        // Get Elements
        browserSwipeRefresh=view.findViewById(R.id.browserSwipeRefresh);
        // Get Root View
        toolbarMain=getActivity().findViewById(R.id.browserMainToolbar);

        // Get WebView Layout
        webViewLayout=view.findViewById(R.id.WebViewLayout);
        // Create WebView
        browserWebView=new OKWebView(getActivity());
        browserWebView.setWebViewClient(new OKWebViewClient());
        browserWebView.setWebChromeClient(new OKWebViewChromeClient());
        browserWebView.setDownloadListener(new OKWebViewDownloadListener());
        browserWebView.setTag("browserWebView");
        browserWebView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        // Register Context Menu
        this.registerForContextMenu(browserWebView);
        // Get Settings
        WebSettings browserWebSetting=browserWebView.getSettings();
        // Get and Set Preference Settings
        int desktopMode=getPreferenceInteger(getPreferences(), "BROWSER_DESKTOP_MODE");
        // Set User Agent for WebView.
        if(desktopMode==0)
            browserWebSetting.setUserAgentString(null);
        else
            browserWebSetting.setUserAgentString(DESKTOP_USER_AGENT);

        // Set Javascript Mode
        int JavascriptMode=getPreferenceInteger(getPreferences(), "BROWSER_JAVASCRIPT");
        browserWebSetting.setJavaScriptEnabled(JavascriptMode==1);
        // Set Geo Location
        int GeoLocation=getPreferenceInteger(getPreferences(), "BROWSER_GEO_LOCATION");
        browserWebSetting.setGeolocationEnabled(GeoLocation==1);
        // Set Popups
        int Popups=getPreferenceInteger(getPreferences(), "BROWSER_POPUPS");
        browserWebSetting.setJavaScriptCanOpenWindowsAutomatically(Popups==1);
        // Set Local Storage
        int LocalStorage=getPreferenceInteger(getPreferences(), "BROWSER_LOCAL_STORAGE");
        browserWebSetting.setDomStorageEnabled(LocalStorage==1);
        // Set Zoom
        int Zoom=getPreferenceInteger(getPreferences(), "BROWSER_ZOOM");
        browserWebSetting.setSupportZoom(Zoom==1);
        // Set Zoom Buttons
        int ZoomButtons=getPreferenceInteger(getPreferences(), "BROWSER_ZOOM_BUTTONS");
        browserWebSetting.setDisplayZoomControls(ZoomButtons==1);
        // Set Cache
        int Cache=getPreferenceInteger(getPreferences(), "BROWSER_APP_CACHE");
        browserWebSetting.setAppCacheEnabled(Cache==1);
        // Set Save Forms
        int SaveForms=getPreferenceInteger(getPreferences(), "BROWSER_SAVE_FORMS");
        browserWebSetting.setSaveFormData(SaveForms==1);
        // Required Settings
        browserWebSetting.setAllowContentAccess(true);
        browserWebSetting.setAllowFileAccess(true);
        browserWebSetting.setBuiltInZoomControls(true);
        browserWebSetting.setDatabaseEnabled(true);
        browserWebSetting.setLoadWithOverviewMode(true);
        browserWebSetting.setUseWideViewPort(true);
        browserWebSetting.setNeedInitialFocus(true);

        // Swipe Refresh
        browserSwipeRefresh.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                // Check Swipe Refresh Enable
                browserSwipeRefresh.setEnabled(getWebView().getScrollY()==0);
            }
        });
        // Swipe Refresh On Refresh Listener
        browserSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Set Refresh Mode
                BROWSER_REFRESH_MODE="Refresh";
                // Refresh WebView
                getWebView().reload();
            }
        });
        // Check Toolbar Swipe Down ( OKWebView.java )

        // Add WebView
        webViewLayout.addView(browserWebView);

        // Check Send URL
        String url = getArguments().getString("webViewLoadUrl");
        int getSearchEngine=getPreferenceInteger(getPreferences(), "BROWSER_SEARCH_ENGINE");
        if(url.equals("")) {
            // Start Home Page URL
            browserWebView.loadUrl(SEARCH_ENGINE_HOME_URLS.get(getSearchEngine));
        }
        else {
            // Start Send Url
            browserWebView.loadUrl(url);
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Set Current Fragment
        setCurrentTabFragment(this);
        // Set Current WebView
        setWebView(this.getView().findViewWithTag("browserWebView"));
    }

    // WebView Context Menu
    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        // Get Hit Result
        WebView.HitTestResult result = browserWebView.getHitTestResult();
        View rootView=browserWebView.getRootView();

        // Get Menu Type
        int type=result.getType();
        if(type == WebView.HitTestResult.IMAGE_TYPE){
            // Open Image Menu
            getImageContextMenu(result.getExtra()).showAtLocation(browserWebView, Gravity.CENTER, 0,0);
            // Set Blur Background
            rootView.setAlpha(0.5F);
        }
        else if(type == WebView.HitTestResult.SRC_ANCHOR_TYPE){
            // Get Title and URL
            getUrlAndTitleJavascript("anchor",result.getExtra(),result);
            // Set Blur Background
            rootView.setAlpha(0.5F);
        }
        else if(type == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
            // Get Anchor URL
            Handler handler=new Handler();
            Message message=handler.obtainMessage();

            browserWebView.requestFocusNodeHref(message);
            String getURL=message.getData().getString("url");

            // Check and Get Title, Url and Image URL
            // If image url 'data: ' is dont open image-anchor menu.
            if(URLUtil.isValidUrl(result.getExtra()))
                getUrlAndTitleJavascript("img-anchor", getURL, result);
            else
                getUrlAndTitleJavascript("anchor", getURL, result);

            // Set Blur Background
            rootView.setAlpha(0.5F);
        }

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    static void getUrlAndTitleJavascript(String type, String url, WebView.HitTestResult result){
        browserWebView.evaluateJavascript("window._touchtarget?window._touchtarget.innerText:''", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String title) {
                // Get Link Title
                getTitle = title.substring(1, (title.length() - 1));
                getUrl=url;

                if(type.equals("img-anchor")) {
                    // Get Handler URL
                    Handler handler=new Handler();
                    Message message=handler.obtainMessage();

                    browserWebView.requestFocusNodeHref(message);
                    getUrl=message.getData().getString("url");
                    // Open Context Menu
                    getImageAnchorContextMenu(getUrl, result.getExtra(), getTitle).showAtLocation(browserWebView, Gravity.CENTER, 0, 0);
                }
                else{
                    // Open Context Menu
                    getAnchorContextMenu(getUrl, getTitle).showAtLocation(browserWebView, Gravity.CENTER, 0, 0);
                }
            }
        });
    }

    public static void setWebView(OKWebView webView){
        browserWebView=webView;
    }
    public static OKWebView getWebView(){
        return browserWebView;
    }
}