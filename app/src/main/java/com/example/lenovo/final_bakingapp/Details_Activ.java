package com.example.lenovo.final_bakingapp;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lenovo on 05/11/2017.
 */

public class Details_Activ extends AppCompatActivity implements Full_Async_task_and_Main_Detail_Exo_Adapters.Detail_Adapt.OnItemClickListener {

    @BindView(R.id.detail_rec)
    RecyclerView rec_view;
    @BindView(R.id.ingr1)
    TextView txt_view;

    Full_Async_task_and_Main_Detail_Exo_Adapters.Detail_Adapt adapt;
    ArrayList<Bak_Attr> step_info;
    public static ArrayList<String> ingr_info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_layout);

        ButterKnife.bind(this);


        ingr_info = getIntent().getStringArrayListExtra("ingred");

        step_info = getIntent().getParcelableArrayListExtra("steps");

        if (ingr_info != null) {
            for (int i = 0; i < ingr_info.size(); i++) {
                txt_view.append(ingr_info.get(i) + "\n");
            }
        }
        setlast(txt_view.getText().toString());
        rec_view.setLayoutManager(new LinearLayoutManager(this));
        adapt = new Full_Async_task_and_Main_Detail_Exo_Adapters.Detail_Adapt(step_info, this );
        rec_view.setAdapter(adapt);


        Intent intent = new Intent(this, WidgetProvider.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        try {
            pendingIntent.send();
        } catch (PendingIntent.CanceledException e) {

        }

    }


    @Override
    public void onItemClick(int item) {
        Intent intent = new Intent(this, Steps_Details_Activ.class);
        intent.putParcelableArrayListExtra("list", step_info);
        intent.putExtra("id_f", item);
        startActivity(intent);
    }

    void setlast(String f) {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putString("bob", f).apply();
    }
}

