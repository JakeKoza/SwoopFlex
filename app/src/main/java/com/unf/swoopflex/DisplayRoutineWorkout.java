package com.unf.swoopflex;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.unf.swoopflex.models.WorkoutModel;

import java.util.List;

/**
 * Created by Ricky on 3/16/2016.
 */
public class DisplayRoutineWorkout extends Fragment {

    TextView work_Name = null;
    TextView work_Descrip = null;
    ImageView equipImage;
    Button prevButton, nextButton, timeButton, resetButton;
    Globals g = Globals.getInstance();
    public List<WorkoutModel> workoutList = g.getWorkoutModelList();
    TextView time;
    long starttime = 0L;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedtime = 0L;
    int t = 1;
    int secs = 0;
    int mins = 0;
    int milliseconds = 0;
    Handler handler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.display_workout_routine, container, false);

        work_Name = (TextView)view.findViewById(R.id.random_workout);
        work_Descrip = (TextView)view.findViewById(R.id.random_description);
        equipImage = (ImageView)view.findViewById(R.id.dis_workimage);
        prevButton = (Button)view.findViewById(R.id.previousWorkout);
        nextButton = (Button)view.findViewById(R.id.nextWorkout);
        timeButton = (Button)view.findViewById(R.id.timeWorkout);
        resetButton = (Button)view.findViewById(R.id.resetTime);
        time = (TextView) view.findViewById(R.id.timer);

        if (g.getPosition() + 1 == g.getWorkoutModelListLength()) {

            nextButton.setText("All Done!");
        }

        if (g.getPosition() - 1 == -1) {

            prevButton.setEnabled(false);

        }

        //ImageLoader.getInstance().displayImage("http://73.35.6.103/images/" + workoutList.get(g.getPosition()).getEquip_ID() + ".jpg", equipImage); // Default options will be used
        ImageLoader.getInstance().displayImage("http://73.35.6.103/images/1.jpg", equipImage); // Default options will be used

        work_Name.setText(workoutList.get(g.getPosition()).getWork_Name());
        work_Descrip.setText(workoutList.get(g.getPosition()).getWork_Descrip());

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (timeButton.getText().toString().equals("Begin Workout")) {
                    timeButton.setText("Pause Workout");
                    starttime = SystemClock.uptimeMillis();
                    handler.postDelayed(updateTimer, 0);
                    t = 0;
                } else {
                    timeButton.setText("Begin Workout");
                    time.setTextColor(Color.parseColor("#2196F3"));
                    timeSwapBuff += timeInMilliseconds;
                    handler.removeCallbacks(updateTimer);
                    t = 1;
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                starttime = 0L;
                timeInMilliseconds = 0L;
                timeSwapBuff = 0L;
                updatedtime = 0L;
                t = 1;
                secs = 0;
                mins = 0;
                milliseconds = 0;
                timeButton.setText("Begin Workout");
                handler.removeCallbacks(updateTimer);
                time.setText("00:00:00");
            }});

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(t == 0){

                    timeSwapBuff += timeInMilliseconds;
                    handler.removeCallbacks(updateTimer);
                }

                g.setTotalTime(updatedtime + g.getTotalTime());

                if (g.getPosition() + 1 == g.getWorkoutModelListLength()) {

                    Toast.makeText(getActivity(), String.valueOf(g.getTotalTime()), Toast.LENGTH_SHORT).show();

                } else {
                    g.setPosition(g.getPosition() + 1);

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
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (g.getPosition() - 1 == -1) {

                    Toast.makeText(getActivity(), "First Workout in Routine", Toast.LENGTH_SHORT).show();

                } else {
                    g.setPosition(g.getPosition() - 1);

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
            }
        });


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

    public Runnable updateTimer = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - starttime;
            updatedtime = timeSwapBuff + timeInMilliseconds;
            secs = (int) (updatedtime / 1000);
            mins = secs / 60;
            secs = secs % 60;
            milliseconds = (int) (updatedtime % 1000);
            time.setText("" + mins + ":" + String.format("%02d", secs) + ":"
                    + String.format("%03d", milliseconds));
            time.setTextColor(Color.RED);
            handler.postDelayed(this, 0);
        }};
}
