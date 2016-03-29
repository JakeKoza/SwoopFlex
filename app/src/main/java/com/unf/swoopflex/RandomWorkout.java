package com.unf.swoopflex;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Jake on 1/21/16.
 */
public class RandomWorkout extends Fragment {

    String[][] dataArray = null;
    HttpConnect http = new HttpConnect();
    TextView work_Name = null;
    TextView work_Descrip = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.random_workout, container, false);

        work_Name = (TextView)view.findViewById(R.id.random_equipment);
        work_Descrip = (TextView)view.findViewById(R.id.random_description);

        new JsonTask().execute("test");
        
        return view;
    }

    public class JsonTask extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... params) {

            String queryData = null;
            queryData = http.GetRandWorkout();

            return queryData;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            dataArray = http.jsonStringArrayParser(result);

            if(dataArray != null) {
                work_Name.setText(String.valueOf(dataArray[0][1]));
                work_Descrip.setText(String.valueOf(dataArray[0][4]));
            }
        }

    }
}
