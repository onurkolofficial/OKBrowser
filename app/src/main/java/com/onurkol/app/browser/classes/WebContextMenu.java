package com.onurkol.app.browser.classes;

import android.app.DownloadManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.onurkol.app.browser.R;

import java.lang.ref.WeakReference;

import static android.content.Context.DOWNLOAD_SERVICE;
import static com.onurkol.app.browser.classes.Main.getActivity;
import static com.onurkol.app.browser.classes.Main.getContext;
import static com.onurkol.app.browser.classes.TabController.newTab;
import static com.onurkol.app.browser.fragments.WebViewFragment.getWebView;

public class WebContextMenu {
    // Get Image Context Menu
    // Note! This(getImageContextMenu) is only image menu.
    // So 'url' is 'image url'.
    // imageUrl parameter is empty or null.
    public static PopupWindow getImageContextMenu(){
        return WebContextMenuClass("image","",null,"");
    }
    public static PopupWindow getImageContextMenu(String url){
        return WebContextMenuClass("image",url,null,"");
    }
    public static PopupWindow getImageContextMenu(String url, String title){
        return WebContextMenuClass("image",url,null,title);
    }

    // Get Anchor Context Menu
    public static PopupWindow getAnchorContextMenu(){
        return WebContextMenuClass("anchor","",null,"");
    }
    public static PopupWindow getAnchorContextMenu(String url){
        return WebContextMenuClass("anchor",url,null,"");
    }
    public static PopupWindow getAnchorContextMenu(String url, String title){
        return WebContextMenuClass("anchor",url,null,title);
    }


    // Get Image Anchor Context Menu
    public static PopupWindow getImageAnchorContextMenu(){
        return WebContextMenuClass("img-anchor","",null,"");
    }
    public static PopupWindow getImageAnchorContextMenu(String webUrl){
        return WebContextMenuClass("img-anchor",webUrl,null,"");
    }
    public static PopupWindow getImageAnchorContextMenu(String webUrl, String imageUrl){
        return WebContextMenuClass("img-anchor",webUrl,imageUrl,"");
    }
    public static PopupWindow getImageAnchorContextMenu(String webUrl, String imageUrl, String title){
        return WebContextMenuClass("img-anchor",webUrl,imageUrl,title);
    }

    // Elements
    static WeakReference<TextView> showUrl,showImageUrl,showWebUrl;
    static WeakReference<Button> openNewTabButton,openUrlNewTabButton,copyUrlButton,copyTitleButton,openImageButton,openImageNewTabButton,downloadImageButton;

    // Values
    static String getURL, getImageURL, getTITLE;

    // Services
    static ClipboardManager clipboard = (ClipboardManager)getContext().getSystemService(Context.CLIPBOARD_SERVICE);

    // Popup Window
    static PopupWindow popupWindow;
    // Main Class
    static PopupWindow WebContextMenuClass(String type, String url, String imageUrl, String title){
        // Set Popup Window
        popupWindow = new PopupWindow(getActivity());

        // Set Values
        getURL=url;
        getTITLE=title;
        if(imageUrl!=null)
            getImageURL=imageUrl;
        else
            getImageURL=url;

        // Print URL
        int limit=50;
        if(url.length()>limit)
            url=url.substring(0,limit)+"...";

        if(type.equals("image")){
            // inflate Layout
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.menu_webview_image, null);
            // Set Popup View
            popupWindow.setContentView(view);

            // Get Elements
            showImageUrl=new WeakReference<TextView>(view.findViewById(R.id.showImageUrl));
            openImageButton=new WeakReference<Button>(view.findViewById(R.id.imageOpenImageButton));
            openImageNewTabButton=new WeakReference<Button>(view.findViewById(R.id.imageOpenImageNewTabButton));
            downloadImageButton=new WeakReference<Button>(view.findViewById(R.id.imageDownloadImageButton));

            // Set Url
            showImageUrl.get().setText(url);

            // Set Listeners
            openImageButton.get().setOnClickListener(openImageListener);
            openImageNewTabButton.get().setOnClickListener(openImageNewTabListener);
            downloadImageButton.get().setOnClickListener(downloadImageListener);
        }
        else if(type.equals("anchor")){
            // inflate Layout
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.menu_webview_anchor, null);
            // Set Popup View
            popupWindow.setContentView(view);

            // Get Elements
            showUrl=new WeakReference<TextView>(view.findViewById(R.id.showUrl));
            openNewTabButton=new WeakReference<Button>(view.findViewById(R.id.anchorOpenNewTabButton));
            copyUrlButton=new WeakReference<Button>(view.findViewById(R.id.anchorCopyUrlButton));
            copyTitleButton=new WeakReference<Button>(view.findViewById(R.id.anchorCopyTitleButton));

            // Set Url
            showUrl.get().setText(url);

            // Set Listeners
            openNewTabButton.get().setOnClickListener(openNewTabListener);
            copyUrlButton.get().setOnClickListener(copyURLListener);
            copyTitleButton.get().setOnClickListener(copyTITLEListener);
        }
        else if(type.equals("img-anchor")){
            // inflate your layout or dynamically add view
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.menu_webview_image_anchor, null);
            // Set Popup View
            popupWindow.setContentView(view);

            // Get Elements
            showWebUrl=new WeakReference<TextView>(view.findViewById(R.id.showWebUrl));
            openUrlNewTabButton=new WeakReference<Button>(view.findViewById(R.id.imageAnchorOpenNewTabButton));
            copyUrlButton=new WeakReference<Button>(view.findViewById(R.id.imageAnchorCopyUrlButton));
            copyTitleButton=new WeakReference<Button>(view.findViewById(R.id.imageAnchorCopyTitleButton));
            openImageButton=new WeakReference<Button>(view.findViewById(R.id.imageAnchorOpenImageButton));
            openImageNewTabButton=new WeakReference<Button>(view.findViewById(R.id.imageAnchorOpenImageNewTabButton));
            downloadImageButton=new WeakReference<Button>(view.findViewById(R.id.imageAnchorDownloadImageButton));

            // Set Url
            showWebUrl.get().setText(url);

            // Set Listeners
            openImageButton.get().setOnClickListener(openImageListener);
            openImageNewTabButton.get().setOnClickListener(openImageNewTabListener);
            downloadImageButton.get().setOnClickListener(downloadImageListener);
            openUrlNewTabButton.get().setOnClickListener(openNewTabListener);
            copyUrlButton.get().setOnClickListener(copyURLListener);
            copyTitleButton.get().setOnClickListener(copyTITLEListener);
        }

        // Set Popup Window Settings
        popupWindow.setFocusable(true);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getContext(),R.color.popupMenuBackground)));
        popupWindow.setElevation(20);

        popupWindow.setOnDismissListener(dismissListenerContextMenu);

        return popupWindow;
    }

    // Listeners
    static PopupWindow.OnDismissListener dismissListenerContextMenu=new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            // Reset Background
            getWebView().getRootView().setAlpha(1);
        }
    };

    static View.OnClickListener openNewTabListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // New Tab URL
            newTab(getURL);
            // Dismiss Popup
            popupWindow.dismiss();
        }
    };
    static View.OnClickListener copyURLListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Copy Data
            ClipData clip = ClipData.newPlainText("copy_url", getURL);
            clipboard.setPrimaryClip(clip);
            // Dismiss Popup
            popupWindow.dismiss();
        }
    };
    static View.OnClickListener copyTITLEListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Copy Data
            ClipData clip = ClipData.newPlainText("copy_title", getTITLE);
            clipboard.setPrimaryClip(clip);
            // Dismiss Popup
            popupWindow.dismiss();
        }
    };
    static View.OnClickListener openImageListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Open Current Tab
            getWebView().loadUrl(getImageURL);
            // Dismiss Popup
            popupWindow.dismiss();
        }
    };
    static View.OnClickListener openImageNewTabListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Open Current Tab
            newTab(getImageURL);
            // Dismiss Popup
            popupWindow.dismiss();
        }
    };
    static View.OnClickListener downloadImageListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(getImageURL));
            request.allowScanningByMediaScanner();

            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            DownloadManager downloadManager = (DownloadManager)getContext().getSystemService(DOWNLOAD_SERVICE);
            downloadManager.enqueue(request);

            Toast.makeText(getContext(),getContext().getString(R.string.downloading_image),Toast.LENGTH_LONG).show();

            // Dismiss Popup
            popupWindow.dismiss();
        }
    };

}
