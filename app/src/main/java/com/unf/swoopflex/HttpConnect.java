package com.unf.swoopflex;

import android.util.Log;

import com.unf.swoopflex.models.WorkoutModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used to connect to the Database via HTTP
 */
public class HttpConnect {

    private HttpURLConnection HttpCon = null;
    private BufferedReader reader = null;

   /** Used for QRcode
    * connects to jsonGetWorkouts.php and passes var data as Id
    *  Id is then used in a query to get all workouts with equip_Id = to Id
    *  The query is then return in json format and loaded into a string
    *  that string is then returned
    */
    public String GetWorkoutByEquipId(String data){

        try{
            //Creates String with parameters to be passed
            String qData = URLEncoder.encode("Id", "UTF-8") + "=" + URLEncoder.encode(data, "UTF-8");
            //Creates URL with parameters that is used to connect to server
            String UrlPath = "http://73.35.6.103/jsonGetWorkout.php" + "?" + qData;
            //Formats string as URL
            URL url = new URL(UrlPath);
            HttpCon = (HttpURLConnection) url.openConnection();
            //Sets Http method to GET
            HttpCon.setRequestMethod("GET");
            HttpCon.connect();

            //reads in data from php file (Json format)
            InputStream stream = HttpCon.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while((line = reader.readLine()) != null){
                buffer.append(line);
            }

            Log.d("MySql", "PHP GetWorkoutByEquipId Return Success");
            return buffer.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d("MySql", "Error MalformedURL");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("MySql", "Error IO");
        }finally{
            //Checks to see if connection was created
            if(HttpCon != null){
                HttpCon.disconnect();
            }
            //Check to see if reader was created
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    /**Used for Random Workout
     * Connects to GetRandWorkout php file
     * php file returns 1 random workout from DB in json format
     * Method returns string in json format
     */
    public String GetRandWorkout(){

        try{

            String UrlPath = "http://73.35.6.103/GetRandWorkout.php";

            URL url = new URL(UrlPath);
            HttpCon = (HttpURLConnection) url.openConnection();

            HttpCon.connect();

            InputStream stream = HttpCon.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();

            String line = "";

            while((line = reader.readLine()) != null){

                buffer.append(line);
            }
            Log.d("MySql", "PHP GetRandWorkout Return Success");

            return buffer.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d("MySql", "Error MalformedURL");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("MySql", "Error IO");
        }finally{
            if(reader != null){
                HttpCon.disconnect();
            }
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    /**
     * Used to generate a workout routine based on the users inputs of workType and workTime
     * @param workType
     * @param workTime
     * @return
     */
    public String GetWorkoutRoutine(String workType, String workTime){

        try{
            String qData = URLEncoder.encode("work_Type", "UTF-8") + "=" + URLEncoder.encode(workType, "UTF-8");
            qData += "&" + URLEncoder.encode("work_Time", "UTF-8") + "=" + URLEncoder.encode(workTime, "UTF-8");

            String UrlPath = "http://73.35.6.103/genWorkoutRoutine.php" + "?" + qData;

            URL url = new URL(UrlPath);
            HttpCon = (HttpURLConnection) url.openConnection();

            HttpCon.connect();

            InputStream stream = HttpCon.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();

            String line = "";

            while((line = reader.readLine()) != null){

                buffer.append(line);
            }
            Log.d("MySql", "PHP GetWorkoutRoutine Return Success");

            return buffer.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d("MySql", "Error MalformedURL");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("MySql", "Error IO");
        }finally{
            if(reader != null){
                HttpCon.disconnect();
            }
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    /**
     * Used to search the database based on workType and workTime
     * @param workType
     * @param workTime
     * @return
     */
    public String searchWorkout(String workType, String workTime){

        try{
            String qData = URLEncoder.encode("work_Type", "UTF-8") + "=" + URLEncoder.encode(workType, "UTF-8");
            qData += "&" + URLEncoder.encode("work_Time", "UTF-8") + "=" + URLEncoder.encode(workTime, "UTF-8");

            String UrlPath = "http://73.35.6.103/searchWorkout.php" + "?" + qData;

            URL url = new URL(UrlPath);
            HttpCon = (HttpURLConnection) url.openConnection();

            HttpCon.connect();

            InputStream stream = HttpCon.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();

            String line = "";

            while((line = reader.readLine()) != null){

                buffer.append(line);
            }
            Log.d("MySql", "PHP GetWorkoutRoutine Return Success");

            return buffer.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d("MySql", "Error MalformedURL");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("MySql", "Error IO");
        }finally{
            if(reader != null){
                HttpCon.disconnect();
            }
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    /**
     * Used to format data returned from the database
     * @param result
     * @return
     */
    public List<WorkoutModel> modelWorkoutArrayParser(String result){

        try {

            JSONArray parentArray = new JSONArray(result);

            List<WorkoutModel> workoutModelList = new ArrayList<>();

            for(int i = 0; i < parentArray.length(); i++) {

                JSONObject finalObject = parentArray.getJSONObject(i);

                WorkoutModel workoutModel = new WorkoutModel();

                workoutModel.setWork_ID(finalObject.getString("work_ID"));
                workoutModel.setWork_Name(finalObject.getString("work_Name"));
                workoutModel.setWork_Type(finalObject.getString("work_Type"));
                workoutModel.setWork_Time(finalObject.getString("work_Time"));
                workoutModel.setWork_Descrip(finalObject.getString("work_Descrip"));
                workoutModel.setWork_Video(finalObject.getString("work_Video"));
                workoutModel.setEquip_ID(finalObject.getString("equip_ID"));
                workoutModelList.add(workoutModel);
            }

            return workoutModelList;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


}
