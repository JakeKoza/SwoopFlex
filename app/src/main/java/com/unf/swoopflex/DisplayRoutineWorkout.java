package com.unf.swoopflex;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        work_Name = (TextView)view.findViewById(R.id.name_workout);
        work_Descrip = (TextView)view.findViewById(R.id.workout_description);
        equipImage = (ImageView)view.findViewById(R.id.dis_workimage);
        prevButton = (Button)view.findViewById(R.id.previousWorkout);
        nextButton = (Button)view.findViewById(R.id.nextWorkout);
        timeButton = (Button)view.findViewById(R.id.timeWorkout);
        resetButton = (Button)view.findViewById(R.id.resetTime);
        time = (TextView) view.findViewById(R.id.timer);

        //checks position for last workout. sets next to all done if on last workout
        if (g.getPosition() + 1 == g.getWorkoutModelListLength()) {

            nextButton.setText("All Done!");
        }

        //checks position for first workout. Disables prev button if on first workout
        if (g.getPosition() - 1 == -1) {

            prevButton.setEnabled(false);

        }

        //Used to load image from database
        //ImageLoader.getInstance().displayImage("http://73.35.6.103/images/" + workoutList.get(g.getPosition()).getEquip_ID() + ".jpg", equipImage); // Default options will be used
        ImageLoader.getInstance().displayImage("http://73.35.6.103/images/1.jpg", equipImage); // Default options will be used

        work_Name.setText(workoutList.get(g.getPosition()).getWork_Name());
        work_Descrip.setText(workoutList.get(g.getPosition()).getWork_Descrip());

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               //Used to start and stop timer
                if (timeButton.getText().toString().equals("Begin Workout")) {
                    //Starts Timer
                    timeButton.setText("Pause Workout");
                    starttime = SystemClock.uptimeMillis();
                    handler.postDelayed(updateTimer, 0);
                    t = 0;
                } else {
                    //Pauses Timer
                    timeButton.setText("Begin Workout");
                    time.setTextColor(Color.parseColor("#2196F3"));
                    timeSwapBuff += timeInMilliseconds;
                    handler.removeCallbacks(updateTimer);
                    t = 1;
                }
            }
        });

        //Used to reset timer
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

                //Checks to see if timer is running. if true stops timer
                if(t == 0){

                    timeSwapBuff += timeInMilliseconds;
                    handler.removeCallbacks(updateTimer);
                }

                //Adds current timer time to global total time
                g.setTotalTime(updatedtime + g.getTotalTime());

                if (g.getPosition() + 1 == g.getWorkoutModelListLength()) {

                    //Toasts global total time
                    Toast.makeText(getActivity(), String.valueOf(g.getTotalTime()), Toast.LENGTH_SHORT).show();

                } else {

                    //sets global position to the next workout in the list
                    g.setPosition(g.getPosition() + 1);

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
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Checks to see if timer is running. if true stops timer
                if(t == 0){

                    timeSwapBuff += timeInMilliseconds;
                    handler.removeCallbacks(updateTimer);
                }

                //Adds current timer time to global total time
                g.setTotalTime(updatedtime + g.getTotalTime());

                //Sets global position to the previous workout in list
                g.setPosition(g.getPosition() - 1);

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
        });


        return view;
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
