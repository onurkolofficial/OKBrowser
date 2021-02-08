package com.onurkol.app.browser.data;

public class HistoryDaysData {
    private String mDays;

    public HistoryDaysData(String Days){
        mDays=Days;
    }

    public void setHistoryDays(String newDays){
        mDays=newDays;
    }
    public String getHistoryDays(){
        return mDays;
    }
}
