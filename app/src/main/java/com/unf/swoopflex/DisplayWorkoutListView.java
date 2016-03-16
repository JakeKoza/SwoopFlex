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

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.unf.swoopflex.models.WorkoutModel;

import java.util.List;

/**
 * Created by Ricky on 3/15/2016.
 */
public class DisplayWorkoutListView extends Fragment {

    Globals g = Globals.getInstance();
    public List<WorkoutModel> workoutList = g.getWorkoutModelList();
    ListView workoutListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.workout_listview, container, false);

        workoutListView = (ListView)view.findViewById(R.id.workout_listview);

        // Create default options which will be used for every
        //  displayImage(...) call if no options will be passed to this method
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getContext()).defaultDisplayImageOptions(defaultOptions).build();
        ImageLoader.getInstance().init(config); // Do it on Application start

        final WorkoutAdapter adapter = new WorkoutAdapter(getContext(), R.layout.workout_listview_display, workoutList);

        workoutListView.setAdapter(adapter);

        Log.d("workoutListView", "return Success");

        workoutListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                WorkoutModel workout = (WorkoutModel) adapter.getItem(position);

                //Toast.makeText(getActivity(), workout.getWork_ID(), Toast.LENGTH_LONG).show();

                g.setPosition(position);

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
        });

        return view;
    }

    public class WorkoutAdapter extends ArrayAdapter{

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
