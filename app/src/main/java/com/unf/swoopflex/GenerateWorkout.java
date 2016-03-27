package com.unf.swoopflex;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.unf.swoopflex.models.WorkoutModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jake on 1/21/16.
 */
public class GenerateWorkout extends Fragment {

    Globals g = Globals.getInstance();
    public List<WorkoutModel> workoutModelList = new ArrayList<>();
    Button genButton;
    Spinner genType;
    Spinner genTime;
    String type, time;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.generate_workout, container, false);

        genButton = (Button)view.findViewById(R.id.generate_workout);
        genType = (Spinner)view.findViewById(R.id.workTypeSpinner);
        genTime = (Spinner)view.findViewById(R.id.workTimeSpinner);

        genButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                time = genTime.getSelectedItem().toString();
                type = genType.getSelectedItem().toString();


                new JsonTask().execute();

                //Toast.makeText(getActivity(), time + type, Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    public class JsonTask extends AsyncTask<String,String,String> {

        HttpConnect http = new HttpConnect();
        String[][] dataArray = null;

        @Override
        protected String doInBackground(String... params) {

            String queryData = null;
            queryData = http.GetWorkoutRoutine(type, time);

            return queryData;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            workoutModelList = http.modelWorkoutArrayParser(result);

            g.setWorkoutModelList(workoutModelList);
            Fragment fragment = null;

            Class fragmentClass = null;

            fragmentClass = DisplayWorkoutListView.class;

            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            DisplayWorkoutListView workoutListView = new DisplayWorkoutListView();

            android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

            fragmentTransaction.replace(R.id.flContent, workoutListView);

            fragmentTransaction.addToBackStack(null);

            fragmentTransaction.commit();
        }

    }
}
