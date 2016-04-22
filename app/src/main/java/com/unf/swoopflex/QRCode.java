package com.unf.swoopflex;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.unf.swoopflex.models.WorkoutModel;

import java.util.List;

/**
 * Class used for QRcode Scanning
 * QRcodes are associated with the Primary key of the equipment
 * Will return all workouts related to the scanned equipment
 */
public class QRCode extends Fragment {

    private String toast;
    private String id;
    private Globals g = Globals.getInstance();
    private List<WorkoutModel> workoutModelList;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        displayToast();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =  inflater.inflate(R.layout.qr_code, container, false);

        g.setFeature(3);

        IntentIntegrator.forSupportFragment(this).initiateScan();

        return view;
    }

    private void displayToast() {
        if(getActivity() != null && toast != null) {
            Toast.makeText(getActivity(), toast, Toast.LENGTH_LONG).show();
            toast = null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {

            if(result.getContents() == null){
                toast = "Scan Cancelled";
                displayToast();
            }else {
                id = result.getContents();
                new JsonTask().execute();
            }
        }
    }

    public class JsonTask extends AsyncTask<String,String,String> {

        HttpConnect http = new HttpConnect();
        String[][] dataArray = null;

        @Override
        protected String doInBackground(String... params) {

            String queryData = null;
            queryData = http.GetWorkoutByEquipId(id);

            return queryData;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if(result.equals("null")){

               Toast.makeText(getActivity(), "Scan Failed Please Try Again", Toast.LENGTH_LONG).show();

               QRCode qrCode = new QRCode();

               android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

               fragmentTransaction.replace(R.id.flContent, qrCode);

               fragmentTransaction.addToBackStack(null);

               fragmentTransaction.commit();

           }else {

                workoutModelList = http.modelWorkoutArrayParser(result);

               g.setWorkoutModelList(workoutModelList);

               DisplayWorkoutListView workoutListView = new DisplayWorkoutListView();

               android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

               fragmentTransaction.replace(R.id.flContent, workoutListView);

               fragmentTransaction.addToBackStack(null);

               fragmentTransaction.commit();
           }
        }


        }

    }
