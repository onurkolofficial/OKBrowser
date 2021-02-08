package com.onurkol.app.browser.classes;

import android.content.Context;
import android.webkit.WebView;

import androidx.annotation.NonNull;

import static com.onurkol.app.browser.activity.MainActivity.hideMainToolbar;
import static com.onurkol.app.browser.activity.MainActivity.showMainToolbar;

public class OKWebView extends WebView {
    public OKWebView(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        if(y<100)
            showMainToolbar();
        else if(y>=100)
            hideMainToolbar();

        super.onScrollChanged(x, y, oldx, oldy);
    }
}
