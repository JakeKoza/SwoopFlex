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

import java.util.List;

/**
 * Class used to search for workouts by body type and time
 */
public class SearchWorkout extends Fragment {

    private List<WorkoutModel> workoutModelList;
    private Button searchButton;
    private Spinner searchType, searchTime;
    private String type, time;
    private Globals g = Globals.getInstance();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.search_workout, container, false);

        //Sets global feature to current feature
        g.setFeature(2);

        searchButton = (Button)view.findViewById(R.id.search_workout);
        searchType = (Spinner)view.findViewById(R.id.searchTypeSpinner);
        searchTime = (Spinner)view.findViewById(R.id.searchTimeSpinner);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                time = searchTime.getSelectedItem().toString();
                type = searchType.getSelectedItem().toString();

                new JsonTask().execute();
            }
        });

        return view;
    }

    public class JsonTask extends AsyncTask<String,String,String> {

        HttpConnect http = new HttpConnect();
        String[][] dataArray = null;

        //Method used to do background task
        @Override
        protected String doInBackground(String... params) {

            String queryData = null;
            queryData = http.searchWorkout(type, time);

            return queryData;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //Gets arraylist from HttpConnect Class
            workoutModelList = http.modelWorkoutArrayParser(result);

            //Sets global arraylist to list returned from DB
            g.setWorkoutModelList(workoutModelList);

            //Code below is used to change fragments
            DisplayWorkoutListView workoutListView = new DisplayWorkoutListView();

            android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

            fragmentTransaction.replace(R.id.flContent, workoutListView);

            fragmentTransaction.addToBackStack(null);

            fragmentTransaction.commit();

        }

    }
}
