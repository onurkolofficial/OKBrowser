package com.onurkol.app.browser.data;

public class HistoryData {
    private String mTitle,mUrl,mIcon,mDate,mId;

    public HistoryData(String Title, String Url, String Icon, String Date, String Id){
        mTitle=Title;
        mUrl=Url;
        mIcon=Icon;
        mDate=Date;
        mId=Id;
    }

    public void setHistoryTitle(String newTitle){
        mTitle=newTitle;
    }
    public String getHistoryTitle(){
        return mTitle;
    }
    public void setHistoryUrl(String newUrl){
        mUrl=newUrl;
    }
    public String getHistoryUrl(){
        return mUrl;
    }
    public void setHistoryIcon(String newIcon){
        mIcon=newIcon;
    }
    public String getHistoryIcon(){
        return mIcon;
    }
    public void setHistoryDate(String newDate){
        mDate=newDate;
    }
    public String getHistoryDate(){
        return mDate;
    }
    public void setHistoryId(String newId){
        mId=newId;
    }
    public String getHistoryId(){
        return mId;
    }
}
