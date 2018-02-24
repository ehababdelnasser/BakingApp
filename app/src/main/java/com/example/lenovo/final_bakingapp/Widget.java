package com.example.lenovo.final_bakingapp;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Lenovo on 10/11/2017.
 */

public class Widget extends Details_Activ implements RemoteViewsService.RemoteViewsFactory {

     Context context;
    ArrayList<String> stringArrayList = new ArrayList<>();
     Intent intent;
    ArrayList<String> ingredData; //null
    TextView t ;

    public Widget(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
    }

    @Override
    public void onCreate() {


    }

    @Override
    public void onDataSetChanged() {
        stringArrayList.clear();



        ingredData = MainActivity.tinyDB.getListString("ingredData");

        if (ingredData != null) {
            for (int i = 0; i < ingredData.size(); i++) {
                String ingredient = ingredData.get(i) + "\n";
                stringArrayList.add(ingredient);
            }//end for
        }

    }
    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (stringArrayList == null || stringArrayList.size() == 0) {
            return 0;
        } else {
            return stringArrayList.size();
        }
    }


    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    public static class Service extends RemoteViewsService {
        @Override
        public RemoteViewsFactory onGetViewFactory(Intent intent) {
            return new Widget(this, intent);
        }
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_provider);
        remoteViews.setTextViewText(R.id.appwidget_text, stringArrayList.get(position));
        return remoteViews;
    }//end remote

}//end class Widget