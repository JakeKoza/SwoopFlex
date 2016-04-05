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
    private List<WorkoutModel> workoutModelList = new ArrayList<>();
    private int position;
    private int feature;
    private long totalTime = 0L;

    private Globals(){};

    public List<WorkoutModel> getWorkoutModelList() {
        return workoutModelList;
    }

    public void setWorkoutModelList(List<WorkoutModel> workoutModelList) {
        this.workoutModelList = workoutModelList;
    }

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
