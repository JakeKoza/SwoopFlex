package com.unf.swoopflex;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

        //ImageLoader.getInstance().displayImage("http://73.35.6.103/images/" + workoutList.get(g.getPosition()).getEquip_ID() + ".jpg", equipImage); // Default options will be used
        ImageLoader.getInstance().displayImage("http://73.35.6.103/images/1.jpg", equipImage); // Default options will be used

        work_Name.setText(workoutList.get(g.getPosition()).getWork_Name());
        work_Descrip.setText(workoutList.get(g.getPosition()).getWork_Descrip());

        return view;
    }


    public class WorkoutAdapter extends ArrayAdapter {

        private int resource;
        private LayoutInflater inflater;
        private List<WorkoutModel> workoutArray;

        public WorkoutAdapter(Context context, int resource, List<WorkoutModel> workoutModel) {
            super(context, resource, workoutModel);

            workoutArray = workoutModel;
            this.resource = resource;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            Log.d("workoutadapter", "Success");
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null){
                convertView = inflater.inflate(resource, null);
            }

            ImageView equipImage;
            TextView workName;

            Log.d("Adapter", "getView Method Success");

            equipImage = (ImageView)convertView.findViewById(R.id.dis_workimage);
            workName = (TextView)convertView.findViewById(R.id.dis_workname);

            // Then later, when you want to display image
            //ImageLoader.getInstance().displayImage("http://73.35.6.103/images/"+workoutArray.get(position).getEquip_ID()+".jpg", equipImage); // Default options will be used
            //Place Holder until we get more images
            ImageLoader.getInstance().displayImage("http://73.35.6.103/images/1.jpg", equipImage); // Default options will be used

            workName.setText(workoutArray.get(position).getWork_Name());

            return convertView;
        }
    }
}
