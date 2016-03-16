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

    private Globals(){};

    public List<WorkoutModel> getWorkoutModelList() {
        return workoutModelList;
    }

    public void setWorkoutModelList(List<WorkoutModel> workoutModelList) {
        this.workoutModelList = workoutModelList;
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
}
