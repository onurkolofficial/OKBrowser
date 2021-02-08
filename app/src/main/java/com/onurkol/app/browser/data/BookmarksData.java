package com.onurkol.app.browser.data;

public class BookmarksData {
    private String mId,mTitle,mUrl;

    public BookmarksData(String Id, String Title, String Url){
        mId=Id;
        mTitle=Title;
        mUrl=Url;
    }

    public void setBookmarkId(String newId){
        mId=newId;
    }
    public String getBookmarkId(){
        return mId;
    }
    public void setBookmarkTitle(String newTitle){
        mTitle=newTitle;
    }
    public String getBookmarkTitle(){
        return mTitle;
    }
    public void setBookmarkUrl(String newUrl){
        mUrl=newUrl;
    }
    public String getBookmarkUrl(){
        return mUrl;
    }

}
