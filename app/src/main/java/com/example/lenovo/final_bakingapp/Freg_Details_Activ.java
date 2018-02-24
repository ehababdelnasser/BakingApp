package com.example.lenovo.final_bakingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Lenovo on 10/11/2017.
 */

public class Freg_Details_Activ extends AppCompatActivity implements Steps_Details_Frag.OnFragmentInteractionListener, Steps_Ingr_Frag.OnFragmentInteractionListener {
    Steps_Details_Frag stepsDetailsFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_detail);

        stepsDetailsFrag = (Steps_Details_Frag) getSupportFragmentManager().findFragmentById(R.id.fragment);
    }

    @Override
    public void onFragmentInteraction(Object uri) {
    }


    @Override
    public void onFragmentInteractionde(int uri) {
        stepsDetailsFrag.solv(uri);
    }
}
