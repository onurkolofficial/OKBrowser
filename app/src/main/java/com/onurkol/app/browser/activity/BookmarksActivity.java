package com.onurkol.app.browser.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.onurkol.app.browser.R;
import com.onurkol.app.browser.classes.BookmarkListAdapter;
import com.onurkol.app.browser.data.BookmarksData;

import java.util.ArrayList;

import static com.onurkol.app.browser.classes.BookmarkController.initBookmarksData;
import static com.onurkol.app.browser.classes.Main.setActivity;
import static com.onurkol.app.browser.classes.Main.setContext;
import static com.onurkol.app.browser.data.BrowserData.BROWSER_BOOKMARKS;

public class BookmarksActivity extends AppCompatActivity {

    ImageButton backButton;
    TextView settingName;
    ListView bookmarksListView;

    BookmarkListAdapter bookmarkAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set Context
        setActivity(this); setContext(this);
        // Create
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);

        // Get Elements
        backButton=findViewById(R.id.backSettingsButton);
        settingName=findViewById(R.id.settingName);
        bookmarksListView=findViewById(R.id.bookmarksList);

        // Set Toolbar Title
        settingName.setText(getString(R.string.bookmarks));

        // Button Click Events
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Close This Activity
                finish();
            }
        });

        // Init List Adapter
        initializeBookmarksList();
        initBookmarksData();
    }

    public void initializeBookmarksList(){
        BROWSER_BOOKMARKS=new ArrayList<BookmarksData>();
        bookmarkAdapter=new BookmarkListAdapter(this,bookmarksListView,BROWSER_BOOKMARKS);
        bookmarksListView.setAdapter(bookmarkAdapter);
    }
}