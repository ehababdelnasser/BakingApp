package com.example.lenovo.final_bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Lenovo on 10/11/2017.
 */

public class Steps_Ingr_Frag extends Fragment implements Full_Async_task_and_Main_Detail_Exo_Adapters.Detail_Adapt.OnItemClickListener {

    RecyclerView rec;
    Full_Async_task_and_Main_Detail_Exo_Adapters.Detail_Adapt adapt;
    ArrayList<Bak_Attr> Step_info;
    ArrayList<String> Ingr;
    private OnFragmentInteractionListener B_Listen;

    public Steps_Ingr_Frag() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.frag_steps_angr_layout, container, false);

        TextView textView = (TextView) rootView.findViewById(R.id.ingr_freg);
        rec = (RecyclerView) rootView.findViewById(R.id.details_rec_freg);
        Ingr = new ArrayList<>();
        Step_info = new ArrayList<>();
        try {
            Ingr = getActivity().getIntent().getStringArrayListExtra("ingred");
            Step_info = getActivity().getIntent().getParcelableArrayListExtra("steps");


        } catch (Exception e) {
            e.printStackTrace();

        }


        for (int i = 0; i < Step_info.size(); i++) {

        }
        if (Ingr != null) {
            for (int i = 0; i < Ingr.size(); i++) {
                textView.append(Ingr.get(i) + "\n");
            }
        }
        setlast(textView.getText().toString());
        rec.setLayoutManager(new LinearLayoutManager(getContext()));
        adapt = new Full_Async_task_and_Main_Detail_Exo_Adapters.Detail_Adapt(Step_info, this);
        rec.setAdapter(adapt);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            B_Listen = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        B_Listen = null;
    }

    @Override
    public void onItemClick(int item) {
        Intent intent = new Intent(getContext(), Steps_Details_Activ.class);
        intent.putParcelableArrayListExtra("list", Step_info);
        intent.putExtra("idx", item);


        B_Listen.onFragmentInteractionde(item);

    }

    interface OnFragmentInteractionListener {

        void onFragmentInteractionde(int uri);
    }

    void setlast(String f) {
        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("eta", f).apply();
    }

}