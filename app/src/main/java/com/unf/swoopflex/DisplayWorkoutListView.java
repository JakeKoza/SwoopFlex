package com.unf.swoopflex;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.unf.swoopflex.models.WorkoutModel;

import java.util.List;

/**
 * Created by Ricky on 3/15/2016.
 */
public class DisplayWorkoutListView extends Fragment {

    Globals g = Globals.getInstance();
    public List<WorkoutModel> workoutList = g.getWorkoutModelList();
    ListView workoutListView;
    Integer feature;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.workout_listview, container, false);

        workoutListView = (ListView)view.findViewById(R.id.workout_listview);

        //creates adpater to load list view
        final WorkoutAdapter adapter = new WorkoutAdapter(getContext(), R.layout.workout_listview_display, workoutList);

        //Sets adapter to workoutListView and executes adapter class code.
        workoutListView.setAdapter(adapter);

        Log.d("workoutListView", "return Success");

        //When workout is clicked method loads workout for display
        workoutListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //gets position of item clicked
                WorkoutModel workout = (WorkoutModel) adapter.getItem(position);

                //Toast.makeText(getActivity(), workout.getWork_ID(), Toast.LENGTH_LONG).show();

                //sets global position to clicked workout
                g.setPosition(position);

                //Gets current feature
                feature = g.getFeature();

                //Checks to see what feature is being used. If 0/genRoutine then load DisplayRoutineWorkout else DisplayWorkout
                //DisplayRoutineWorkout is used only for generating routines feature
                //DisplayWorkout is used for all other features
                if(feature == 0 ) {

                    //Code below is used to change fragments.
                    Fragment fragment = null;

                    Class fragmentClass = null;

                    fragmentClass = DisplayRoutineWorkout.class;

                    try {
                        fragment = (Fragment) fragmentClass.newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    DisplayRoutineWorkout workoutRoutineDisplay = new DisplayRoutineWorkout();

                    android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                    fragmentTransaction.replace(R.id.flContent, workoutRoutineDisplay);

                    fragmentTransaction.addToBackStack(null);

                    fragmentTransaction.commit();
                }
                else{

                    //Code below is used to change fragments.
                    Fragment fragment = null;

                    Class fragmentClass = null;

                    fragmentClass = DisplayWorkout.class;

                    try {
                        fragment = (Fragment) fragmentClass.newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    DisplayWorkout workoutDisplay = new DisplayWorkout();

                    android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                    fragmentTransaction.replace(R.id.flContent, workoutDisplay);

                    fragmentTransaction.addToBackStack(null);

                    fragmentTransaction.commit();
                }
            }
        });

        return view;
    }

    //Adapter class is needed to load listview
    public class WorkoutAdapter extends ArrayAdapter{

        private int resource;
        private LayoutInflater inflater;
        private List<WorkoutModel> workoutArray;

        //Method used to do background task
        public WorkoutAdapter(Context context, int resource, List<WorkoutModel> workoutModel) {
            super(context, resource, workoutModel);

            workoutArray = workoutModel;
            this.resource = resource;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            Log.d("workoutadapter", "Success");
        }

        //Method used to load each item in the list view
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
