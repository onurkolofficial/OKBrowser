package com.onurkol.app.browser.classes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.onurkol.app.browser.R;
import com.onurkol.app.browser.activity.MainActivity;
import com.onurkol.app.browser.data.BookmarksData;

import java.util.ArrayList;
import java.util.List;

import static com.onurkol.app.browser.classes.BookmarkController.removeBookmark;
import static com.onurkol.app.browser.classes.Main.getActivity;
import static com.onurkol.app.browser.fragments.WebViewFragment.getWebView;

public class BookmarkListAdapter extends ArrayAdapter<BookmarksData> {
    private final LayoutInflater inflater;
    private ViewHolder holder;
    private static List<BookmarksData> bookmarksData;
    private final ListView bookmarkListView;

    public BookmarkListAdapter(Context context, ListView mBookmarkListView, List<BookmarksData> mBookmarkData){
        super(context,0,mBookmarkData);
        bookmarksData=mBookmarkData;
        bookmarkListView=mBookmarkListView;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return bookmarksData.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView==null){
            convertView=inflater.inflate(R.layout.bookmarks_items, null);
            holder=new ViewHolder();
            holder.bookmarkTitle=convertView.findViewById(R.id.bookmarkTitle);
            holder.bookmarkUrl=convertView.findViewById(R.id.bookmarkUrl);
            holder.bookmarkDelete=convertView.findViewById(R.id.bookmarkDeleteButton);
            holder.bookmarkOpen=convertView.findViewById(R.id.openBookmarkButton);
            convertView.setTag(holder);
        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }

        // Get Data
        final BookmarksData data=bookmarksData.get(position);
        // Write Data
        // Url Character Limit
        String url=data.getBookmarkUrl();
        if(url.length()>40)
            url=data.getBookmarkUrl().substring(0,38)+"..."; // Max. 38 char.
        // Title Character Limit
        String title=data.getBookmarkTitle();
        if(title.length()>25)
            title=data.getBookmarkTitle().substring(0,25)+"..."; // Max. 25 char.

        // Set Bookmark Title
        holder.bookmarkTitle.setText(title);
        holder.bookmarkUrl.setText(url);

        holder.bookmarkDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Convert Data
                String gId=data.getBookmarkId();
                String gTitle=data.getBookmarkTitle();
                String gUrl=data.getBookmarkUrl();
                // Remove Data
                removeBookmark(gId,gTitle,gUrl);
                // Remove ListView Data
                bookmarksData.remove(position);
                bookmarkListView.invalidateViews();
            }
        });

        holder.bookmarkOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check Open from Shortcut
                if(getWebView()==null){
                    // Get Activity
                    Activity $this=getActivity();
                    // Create new Bundle
                    Bundle bundle = new Bundle();
                    // Set bundle Datas
                    bundle.putString("ACTIVITY_MODE","OPEN_SHORTCUT_BOOKMARK");
                    bundle.putString("BOOKMARK_URL",data.getBookmarkUrl());
                    // Create Main Activity
                    Intent mainActivity=new Intent($this,MainActivity.class);
                    // Put Intent
                    mainActivity.putExtras(bundle);
                    $this.startActivity(mainActivity);
                    // Close this activity
                    $this.finish();
                }
                else {
                    // Load Web Page
                    getWebView().loadUrl(data.getBookmarkUrl());
                    // Finish Activity
                    getActivity().finish();
                }
            }
        });

        return convertView;
    }

    //View Holder
    private static class ViewHolder {
        TextView bookmarkTitle,bookmarkUrl;
        ImageButton bookmarkDelete;
        LinearLayout bookmarkOpen;
    }
}
