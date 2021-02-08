package com.onurkol.app.browser.classes;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.onurkol.app.browser.R;
import com.onurkol.app.browser.data.TabData;

import static com.onurkol.app.browser.classes.HistoryController.addHistory;
import static com.onurkol.app.browser.classes.Main.getTabListView;
import static com.onurkol.app.browser.classes.Main.hideKeyboard;
import static com.onurkol.app.browser.classes.TabListAdapter.getCurrentTabItemPosition;
import static com.onurkol.app.browser.classes.TabListAdapter.getTabData;
import static com.onurkol.app.browser.data.BrowserData.BROWSER_REFRESH_MODE;
import static com.onurkol.app.browser.data.BrowserData.BROWSER_REFRESH_STATUS;
import static com.onurkol.app.browser.data.BrowserData.BROWSER_START_NEW_TAB;
import static com.onurkol.app.browser.data.PreferenceData.getPreferenceInteger;
import static com.onurkol.app.browser.data.PreferenceData.getPreferences;

public class OKWebViewClient extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return false;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        // Get Root View
        View rootView=view.getRootView();
        // Get Elements
        EditText browserSearch=rootView.findViewById(R.id.browserSearch);

        // Hide Virtual Keyboard
        hideKeyboard(view);

        // Set URL Title
        browserSearch.setText(view.getUrl());

        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        // <Fixed> Called Twice Finish.
        if(!BROWSER_REFRESH_STATUS){
            // Loading Success

            // Get Root View
            View rootView=view.getRootView().getRootView();
            // Get Elements
            EditText browserSearch=rootView.findViewById(R.id.browserSearch);
            SwipeRefreshLayout browserSwipeRefresh=rootView.findViewById(R.id.browserSwipeRefresh);

            // Get Active Tab
            int tab=getCurrentTabItemPosition();
            // Get Tab Data
            final TabData data=getTabData().get(tab);
            // <Fixed> getChildAt returning null.
            int getTabViewId=getTabListView().getLastVisiblePosition() - getTabListView().getFirstVisiblePosition();
            // Check Exist Tab
            if(getTabListView().getChildAt(getTabViewId)!=null){
                // Get Tab View
                View tabView = getTabListView().getChildAt(getTabViewId);
                // Get Update View
                ImageView imagePreview = tabView.findViewById(R.id.tabPreview);
                // Set Tab Preview
                // Get WebView Drawable
                Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
                final Canvas c = new Canvas(bitmap);
                view.draw(c);
                // Print Tab image
                imagePreview.setImageBitmap(bitmap);
                data.setWebPreviewBitmap(bitmap);

                // Set Page Title
                data.setTitle(view.getTitle());
                getTabListView().invalidateViews();

                // ** NEXT VERSIONS **
                //Log.e("99/WebViewClient","Get Favicon: "+view.getFavicon());

                // Check And Save History
                int getIncognitoMode=getPreferenceInteger(getPreferences(),"BROWSER_INCOGNITO_MODE");
                if(getIncognitoMode==0){
                    // If first start browser or add new tab to history dont save.
                    if(!BROWSER_START_NEW_TAB) {
                        if(!BROWSER_REFRESH_MODE.equals("Refresh"))
                            addHistory(view.getTitle(), view.getUrl(), "");
                    }
                    // Reset Status
                    BROWSER_START_NEW_TAB=false;
                    BROWSER_REFRESH_MODE="";
                }

                // Set URL Title
                browserSearch.setText(view.getUrl());

                // Stop Refresh
                browserSwipeRefresh.setRefreshing(false);
            }
        }

        // Get URL Title from Javascript
        view.evaluateJavascript("const w=window;\n" +
                "function wrappedOnDownFunc(e){\n" +
                "  w._touchtarget = e.touches[0].target;\n" +
                "}\n"+
                "w.addEventListener('touchstart',wrappedOnDownFunc);\n", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s){}
        });
    }
}
