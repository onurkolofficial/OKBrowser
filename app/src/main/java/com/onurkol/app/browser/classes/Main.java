package com.onurkol.app.browser.classes;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.onurkol.app.browser.R;
import com.onurkol.app.browser.activity.MainActivity;

import java.lang.ref.WeakReference;

public class Main {
    // Fixed 'Memory Leak' warning.
    private static WeakReference<Activity> mActivity;
    private static WeakReference<Context> mContext;
    private static WeakReference<LinearLayout> browserTabsLayout;
    private static WeakReference<ListView> TabListView;

    static InputMethodManager inputMethodManager;

    public static void initStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getActivity().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // Request Permission.
                getActivity().requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    public static Drawable getStringDrawable(String DrawableName) {
        return ContextCompat.getDrawable(getContext(),getContext().getResources().getIdentifier(DrawableName,"drawable", getContext().getPackageName()));
    }

    public static void hideKeyboard(View view){
        inputMethodManager=(InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        // Hide Virtual Keyboard
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public static void showKeyboard(View view){
        inputMethodManager=(InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        // Hide Virtual Keyboard
        inputMethodManager.showSoftInput(view,0);
    }

    public static void setBrowserTabsLayout(LinearLayout layout){
        browserTabsLayout=new WeakReference<LinearLayout>(layout);
    }
    public static LinearLayout getBrowserTabsLayout(){
        return browserTabsLayout.get();
    }

    public static void setTabListView(ListView mTabListView){
        TabListView=new WeakReference<ListView>(mTabListView);
    }
    public static ListView getTabListView(){
        return TabListView.get();
    }

    public static void setActivity(Activity activity){
        mActivity=new WeakReference<Activity>(activity);
    }
    public static void setContext(Context context){
        mContext=new WeakReference<Context>(context);
    }
    public static Activity getActivity(){
        return mActivity.get();
    }
    public static Context getContext(){
        return mContext.get();
    }

}
