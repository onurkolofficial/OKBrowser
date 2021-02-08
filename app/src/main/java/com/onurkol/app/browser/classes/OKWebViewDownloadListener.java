package com.onurkol.app.browser.classes;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.widget.Toast;

import com.onurkol.app.browser.R;

import static android.content.Context.DOWNLOAD_SERVICE;
import static com.onurkol.app.browser.classes.Main.getContext;
import static com.onurkol.app.browser.classes.Main.initStoragePermission;

public class OKWebViewDownloadListener implements DownloadListener {
    @Override
    public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
        // Check Storage Permissions
        initStoragePermission();
        // Start Download
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

        // Get Strings
        String downloading_text=getContext().getString(R.string.downloading_text);
        String downloading_file_text=getContext().getString(R.string.downloading_file);

        request.setDescription(downloading_text);
        request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimetype));
        request.allowScanningByMediaScanner();
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,URLUtil.guessFileName(url, contentDisposition, mimetype));
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        DownloadManager downloadManager = (DownloadManager) getContext().getSystemService(DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);

        Toast.makeText(getContext(), downloading_file_text, Toast.LENGTH_LONG).show();
    }
}
