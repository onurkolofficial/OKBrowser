package com.onurkol.app.browser.data;

import android.graphics.Bitmap;

public class TabData {
    private String webTitle;
    private Bitmap webPreviewBitmap;

    public TabData(String webTitle,Bitmap bitmap){
        if(this.webPreviewBitmap!=null)
            this.webPreviewBitmap=bitmap;
        this.webTitle=webTitle;
    }

    public void setWebPreviewBitmap(Bitmap bitmap){
        this.webPreviewBitmap=bitmap;
    }
    public Bitmap getWebPreviewBitmap(){
        return webPreviewBitmap;
    }

    public void setTitle(String Title){
        this.webTitle=Title;
    }
    public String getTitle(){
        return webTitle;
    }
}
