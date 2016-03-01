package com.unf.swoopflex;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Jake on 1/21/16.
 */
public class GenerateWorkout extends Fragment {

    Button genButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.generate_workout, container, false);

        genButton = (Button)view.findViewById(R.id.generate_workout);

        genButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //new JsonTask().execute("test");

                //Toast.makeText(getActivity(), "Equipment ID ="+Id, Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

}
