package com.example.lenovo.final_bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by Lenovo on 07/11/2017.
 */

public class Steps_Details_Activ extends AppCompatActivity implements Steps_Details_Frag.OnFragmentInteractionListener {
    ArrayList<Bak_Attr> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.steps_details_layout);
    }

    @Override
    public void onFragmentInteraction(Object uri) {

    }
}
