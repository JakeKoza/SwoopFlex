package com.unf.swoopflex;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.unf.swoopflex.models.WorkoutModel;

import java.util.List;

/**
 * Created by Jake on 1/21/16.
 */
public class RandomWorkout extends Fragment {

    TextView work_Name = null;
    TextView work_Descrip = null;
    TextView link;
    ImageView equipImage;
    Globals g = Globals.getInstance();
    public List<WorkoutModel> workoutModelList = g.getWorkoutModelList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.display_workout, container, false);

        work_Name = (TextView)view.findViewById(R.id.random_workout);
        work_Descrip = (TextView)view.findViewById(R.id.random_description);
        equipImage = (ImageView)view.findViewById(R.id.dis_workimage);
        link = (TextView) view.findViewById(R.id.link);


        new JsonTask().execute();

        return view;
    }

    public class JsonTask extends AsyncTask<String,String,String> {

        HttpConnect http = new HttpConnect();
        String[][] dataArray = null;

        //Method used to do background task
        //Calls httpconnect class get randworkout that returns a random workout from the external DB
        @Override
        protected String doInBackground(String... params) {

            String queryData = null;
            queryData = http.GetRandWorkout();

            return queryData;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            workoutModelList = http.modelWorkoutArrayParser(result);
            
            //Sets global arraylist to list returned from DB
            g.setWorkoutModelList(workoutModelList);

            //Sets workout information from array list
            work_Name.setText(workoutModelList.get(0).getWork_Name());
            work_Descrip.setText(workoutModelList.get(0).getWork_Descrip());
            link.setText(Html.fromHtml("<a href=" + workoutModelList.get(g.getPosition()).getWork_Video() + ">YouTube</a>"));
            link.setClickable(true);
            link.setMovementMethod(LinkMovementMethod.getInstance());

            //Used to load Image from DB
            //ImageLoader.getInstance().displayImage("http://73.35.6.103/images/"+workoutArray.get(position).getEquip_ID()+".jpg", equipImage); // Default options will be used
            //Place Holder until we get more images
            ImageLoader.getInstance().displayImage("http://73.35.6.103/images/1.jpg", equipImage); // Default options will be used

        }

    }
}
