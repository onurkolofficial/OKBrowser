package com.onurkol.app.browser.classes;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.onurkol.app.browser.data.BookmarksData;

import java.util.ArrayList;
import java.util.List;

import static com.onurkol.app.browser.classes.OKRandom.randString;
import static com.onurkol.app.browser.data.BrowserData.BROWSER_BOOKMARKS;
import static com.onurkol.app.browser.data.PreferenceData.getPreferenceString;
import static com.onurkol.app.browser.data.PreferenceData.getPreferences;
import static com.onurkol.app.browser.data.PreferenceData.setPreferenceString;

public class BookmarkController {
    // Gson
    static Gson gson=new Gson();

    public static void addBookmark(String title, String url){
        // Create Id (for delete data)
        String bookmarkId=randString(10);
        // Add new Data
        BookmarksData getBookmarksData=new BookmarksData(bookmarkId,title,url);
        // Convert Data
        String convertData="["+gson.toJson(getBookmarksData)+"]";
        String oldData=getPreferenceString(getPreferences(), "BROWSER_BOOKMARKS");
        String mergeData="";
        String newData=convertData;
        // Check Data
        if(!oldData.equals("NULL")){
            // Remove array sembol '[' & ']'
            convertData=convertData.substring(1,(convertData.length()-1));
            oldData=oldData.substring(1,(oldData.length()-1));
            // Add new Data
            mergeData=convertData+","+oldData;
            // Add Array symbol '[{data},{old}]'
            newData="["+mergeData+"]";
        }
        // Add View
        BROWSER_BOOKMARKS.add(getBookmarksData);
        // Save Data
        setPreferenceString(getPreferences(), "BROWSER_BOOKMARKS", newData);
    }

    public static void removeBookmark(String id, String title, String url){
        // Get Preference Data
        String bookmarkData=getPreferenceString(getPreferences(), "BROWSER_BOOKMARKS");
        // Convert Get Data
        BookmarksData convertData=new BookmarksData(id,title,url);
        // Get Json
        String deleteData=gson.toJson(convertData);
        String newData="",deleteConvert="";
        // Search Data Position
        if (bookmarkData.contains(deleteData + ","))
            deleteConvert = deleteData + ",";
        else if (bookmarkData.contains("," + deleteData))
            deleteConvert = "," + deleteData;
        else
            deleteConvert = deleteData;
        newData=bookmarkData.replace(deleteConvert,"");

        // <Fixed> If empty data is set NULL value
        if(newData.equals("[]"))
            newData="NULL";
        // Save Data
        setPreferenceString(getPreferences(), "BROWSER_BOOKMARKS", newData);
    }

    public static void initBookmarksData(){
        // Get Preference Data
        String bookmarks=getPreferenceString(getPreferences(), "BROWSER_BOOKMARKS");
        if(!bookmarks.equals("NULL")){
            List<BookmarksData> getBookmarksData=gson.fromJson(bookmarks, new TypeToken<ArrayList<BookmarksData>>(){}.getType());
            int i=0;
            while(i<getBookmarksData.size()){
                // Get Data
                String gId=getBookmarksData.get(i).getBookmarkId();
                String gTitle=getBookmarksData.get(i).getBookmarkTitle();
                String gUrl=getBookmarksData.get(i).getBookmarkUrl();
                // Add View
                BROWSER_BOOKMARKS.add(new BookmarksData(gId,gTitle,gUrl));
                // Count
                i++;
            }
        }
    }

}
