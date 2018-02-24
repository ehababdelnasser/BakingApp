package com.example.lenovo.final_bakingapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.mukesh.tinydb.TinyDB;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lenovo on 03/11/2017.
 */

public class MainActivity extends AppCompatActivity implements Full_Async_task_and_Main_Detail_Exo_Adapters.Exo_Interface {

    @BindView(R.id.main_rec) RecyclerView recyclerView;

    Full_Async_task_and_Main_Detail_Exo_Adapters.Rec_Adapt recAdapt;

    final String URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public static TinyDB tinyDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        tinyDB = new TinyDB(this);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        else
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));


        if (isNetworkAvailable()) {
            Toast.makeText(this, "wait for data", Toast.LENGTH_SHORT).show();
            updateTask(URL);
        } else
            Toast.makeText(this, "not online!", Toast.LENGTH_SHORT).show();


    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void updateTask(String url) {
        Full_Async_task_and_Main_Detail_Exo_Adapters fullAsyncTaskandMainDetailExoAdapters = new Full_Async_task_and_Main_Detail_Exo_Adapters(this);
        fullAsyncTaskandMainDetailExoAdapters.execute(url);
    }

    @Override
    public void onTaskComplete(final List<?> result) {

        recAdapt = new Full_Async_task_and_Main_Detail_Exo_Adapters.Rec_Adapt((List<Bak_Attr>) result, new Full_Async_task_and_Main_Detail_Exo_Adapters.Rec_Adapt.OnItemClickListener() {

            @Override
            public void onItemClick(Bak_Attr item) {
                if (item == null) {
                    Toast.makeText(MainActivity.this, "no items found", Toast.LENGTH_SHORT).show();
                } else
                    call(item);

            }
        });

        recyclerView.setAdapter(recAdapt);
        recAdapt.notifyDataSetChanged();
    }

    void call(Bak_Attr item) {
        Configuration configuration = this.getResources().getConfiguration();
        int screenWidthDp = configuration.screenWidthDp;

        if (screenWidthDp >= 600) {
            Intent intent = new Intent(MainActivity.this, Freg_Details_Activ.class);

            intent.putStringArrayListExtra("ingred", item.getIngrediantList());
            intent.putParcelableArrayListExtra("steps", item.getStepsAttrList());
            intent.putParcelableArrayListExtra("list", item.getStepsAttrList());
            intent.putExtra("idx", 0);

            tinyDB.putListString("ingredData",item.getIngrediantList());
            startActivity(intent);

        } else {
            Intent intent = new Intent(MainActivity.this, Details_Activ.class);

            intent.putStringArrayListExtra("ingred", item.getIngrediantList());
            intent.putParcelableArrayListExtra("steps", item.getStepsAttrList());


            tinyDB.putListString("ingredData",item.getIngrediantList());
            startActivity(intent);
        }

    }
}