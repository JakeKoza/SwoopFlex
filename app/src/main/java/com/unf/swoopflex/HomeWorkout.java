package com.unf.swoopflex;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Ricky on 1/31/16.
 */
public class HomeWorkout extends Fragment implements View.OnClickListener {
    Button btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.client_information_form, container, false);

        btn = (Button) view.findViewById(R.id.btnSubmit);
        btn.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        Toast.makeText(this.getActivity(),
                "Information Saved", Toast.LENGTH_LONG).show();
    }
}

