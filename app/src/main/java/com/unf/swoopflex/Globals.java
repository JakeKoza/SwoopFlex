package com.unf.swoopflex;

import android.app.Application;

import com.unf.swoopflex.models.WorkoutModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ricky on 3/15/2016.
 */
public class Globals extends Application{

    private static Globals instance;
    //Global Var used to hold List of workouts returned from DB
    private List<WorkoutModel> workoutModelList = new ArrayList<>();
    //Global Var used to hold position of desired workout in Arraylist
    private int position;
    //Global Var used to hold selected feature
    private int feature;
    //Global Var used to hold Total Time for one routine
    private long totalTime = 0L;

    private Globals(){};

    //Each Var above has a get and set Method found below

    public List<WorkoutModel> getWorkoutModelList() {
        return workoutModelList;
    }

    public void setWorkoutModelList(List<WorkoutModel> workoutModelList) {
        this.workoutModelList = workoutModelList;
    }

    //Returns length of ListArray
    //Used in displayRoutineWorkout for next and previous buttons
    public Integer getWorkoutModelListLength(){

        return workoutModelList.size();
    }

    public static synchronized Globals getInstance(){
        if(instance==null){
            instance = new Globals();
        }
        return instance;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getFeature() {
        return feature;
    }

    public void setFeature(int feature) {
        this.feature = feature;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }
}
