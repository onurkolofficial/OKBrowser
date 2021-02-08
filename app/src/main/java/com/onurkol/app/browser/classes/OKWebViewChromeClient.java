package com.onurkol.app.browser.classes;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.onurkol.app.browser.R;

import static com.onurkol.app.browser.data.BrowserData.BROWSER_REFRESH_STATUS;

public class OKWebViewChromeClient extends WebChromeClient {
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        // Get RootView
        View rootView = view.getRootView();

        // Get Elements
        EditText browserSearch = rootView.findViewById(R.id.browserSearch);
        ProgressBar browserProgress = rootView.findViewById(R.id.browserProgressbar);

        // Loading Progressbar
        if (newProgress >= 100) {
            browserProgress.setProgress(0);
            // Set refresh status
            BROWSER_REFRESH_STATUS = false;
        } else {
            browserProgress.setProgress(newProgress);
            // Set refresh status
            BROWSER_REFRESH_STATUS = true;
        }

        super.onProgressChanged(view, newProgress);
    }

    @Override
    public void onReceivedIcon(WebView view, Bitmap icon) {
        super.onReceivedIcon(view, icon);
    }
}
