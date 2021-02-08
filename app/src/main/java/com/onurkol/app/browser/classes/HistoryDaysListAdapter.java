package com.onurkol.app.browser.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.onurkol.app.browser.R;
import com.onurkol.app.browser.data.HistoryData;
import com.onurkol.app.browser.data.HistoryDaysData;

import java.util.ArrayList;
import java.util.List;

import static com.onurkol.app.browser.classes.HistoryController.initHistoryData;
import static com.onurkol.app.browser.data.BrowserData.BROWSER_HISTORY_SITES;

public class HistoryDaysListAdapter extends ArrayAdapter<HistoryDaysData> {
    private final LayoutInflater inflater;
    private ViewHolder holder;
    private static List<HistoryDaysData> mHistoryDaysData;
    public HistoryListAdapter historyListAdapter;


    public HistoryDaysListAdapter(Context context, ListView historyDaysListView, List<HistoryDaysData> historyDaysData) {
        super(context, 0, historyDaysData);
        mHistoryDaysData=historyDaysData;
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView==null){
            convertView=inflater.inflate(R.layout.history_day_items, null);
            holder=new ViewHolder();
            holder.historyDateText=convertView.findViewById(R.id.historyDayDate);
            holder.historyListLayout=convertView.findViewById(R.id.historyList);
            convertView.setTag(holder);

            // Get Data
            final HistoryDaysData hdaysData=mHistoryDaysData.get(position);
            String whichDate=hdaysData.getHistoryDays();
            // Set Days Title
            holder.historyDateText.setText(whichDate);

            // Init List Adapter
            initializeHistoryView();
            initHistoryData(holder.historyListLayout,historyListAdapter,whichDate);
        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }

        return convertView;
    }

    public void initializeHistoryView(){
        BROWSER_HISTORY_SITES=new ArrayList<HistoryData>();
        historyListAdapter=new HistoryListAdapter(getContext(),holder.historyListLayout,BROWSER_HISTORY_SITES);
    }

    //View Holder
    private static class ViewHolder {
        TextView historyDateText;
        LinearLayout historyListLayout;
    }
}
