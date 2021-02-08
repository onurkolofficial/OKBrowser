package com.onurkol.app.browser.classes;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.onurkol.app.browser.R;
import com.onurkol.app.browser.activity.MainActivity;
import com.onurkol.app.browser.data.TabData;

import java.util.ArrayList;

import static com.onurkol.app.browser.classes.Main.getBrowserTabsLayout;
import static com.onurkol.app.browser.classes.TabController.closeTab;
import static com.onurkol.app.browser.classes.TabController.startTab;

public class TabListAdapter extends ArrayAdapter<TabData> {
    private final LayoutInflater inflater;
    private ViewHolder holder;
    private static ArrayList<TabData> tabData;
    private final ListView tabListView;
    private static int CurrentTabItemPosition;

    public TabListAdapter(Context context, ListView tabListView, ArrayList<TabData> mTabData){
        super(context, 0, mTabData);
        tabData=mTabData;
        this.tabListView=tabListView;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return tabData.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView==null){
            convertView=inflater.inflate(R.layout.tab_items, null);
            holder=new ViewHolder();
            holder.tabUrlText=convertView.findViewById(R.id.tabUrlText);
            holder.closeTabButton=convertView.findViewById(R.id.closeTabButton);
            holder.tabPreview=convertView.findViewById(R.id.tabPreview);
            holder.openTabButton=convertView.findViewById(R.id.openTabButton);
            convertView.setTag(holder);
        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }

        // Get Data
        final TabData data=tabData.get(position);

        // Title Character Limit
        String title=data.getTitle();
        if(title.length()>20)
            title=data.getTitle().substring(0,20)+"..."; // Max. 20 char.

        // Set Tab Title
        holder.tabUrlText.setText(title);
        // Set Tab Image
        holder.tabPreview.setImageBitmap(data.getWebPreviewBitmap());

        holder.closeTabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Remove Tab Data & Remove Fragment
                closeTab(position);
                // Remove ListView Data
                tabData.remove(position);
                tabListView.invalidateViews();
                // Update Tab Data
                setTabData(tabData);
            }
        });
        holder.openTabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Save Tab Index
                setCurrentTabItemPosition(position);
                // Call Tab Fragment
                startTab(position);
                // Hide Drawer
                MainActivity.browserTabsView.closeDrawer(getBrowserTabsLayout());
            }
        });
        return convertView;
    }

    public static void setCurrentTabItemPosition(int newPosition){
        CurrentTabItemPosition=newPosition;
    }
    public static int getCurrentTabItemPosition(){
        return CurrentTabItemPosition;
    }

    public static void setTabData(ArrayList<TabData> mTabData){
        tabData=mTabData;
    }
    public static ArrayList<TabData> getTabData(){
        return tabData;
    }

    //View Holder
    private static class ViewHolder {
        TextView tabUrlText;
        ImageButton closeTabButton;
        ImageView tabPreview;
        CardView openTabButton;
    }
}
