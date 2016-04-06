package com.unf.swoopflex;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.unf.swoopflex.models.WorkoutModel;

import java.util.List;

/**
 * Created by Ricky on 3/16/2016.
 */
public class DisplayWorkout extends Fragment {

    TextView work_Name = null;
    TextView work_Descrip = null;
    ImageView equipImage;
    Globals g = Globals.getInstance();
    public List<WorkoutModel> workoutList = g.getWorkoutModelList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.display_workout, container, false);

        work_Name = (TextView)view.findViewById(R.id.random_workout);
        work_Descrip = (TextView)view.findViewById(R.id.random_description);
        equipImage = (ImageView)view.findViewById(R.id.dis_workimage);

        //Used to load image from database
        //ImageLoader.getInstance().displayImage("http://73.35.6.103/images/" + workoutList.get(g.getPosition()).getEquip_ID() + ".jpg", equipImage); // Default options will be used
        ImageLoader.getInstance().displayImage("http://73.35.6.103/images/1.jpg", equipImage); // Default options will be used

        //Sets workout information to be displayed
        work_Name.setText(workoutList.get(g.getPosition()).getWork_Name());
        work_Descrip.setText(workoutList.get(g.getPosition()).getWork_Descrip());

        return view;
    }

}
