package com.unf.swoopflex;

import android.util.Log;

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

/**
 * Created by Ricky on 2/28/2016.
 */
public class HttpConnect {

    HttpURLConnection HttpCon = null;
    BufferedReader reader = null;

   //using GET
    public String GetWorkoutByEquipId(String data){

        try{
            String qData = URLEncoder.encode("Id", "UTF-8") + "=" + URLEncoder.encode(data, "UTF-8");

            String UrlPath = "http://73.35.6.103/jsonGetWorkout.php" + "?" + qData;

            URL url = new URL(UrlPath);
            HttpCon = (HttpURLConnection) url.openConnection();

            HttpCon.setRequestMethod("GET");
            HttpCon.connect();

            InputStream stream = HttpCon.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();

            String line = "";

            while((line = reader.readLine()) != null){

                buffer.append(line);
            }
            Log.d("MySql", "Return Success");
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
            Log.d("MySql", "Return Success");

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

    public String[][] jsonStringArrayParser(String result){

        try {

            JSONArray parentArray = new JSONArray(result);

            String[][] dataArray = new String[parentArray.length()][7];

            for(int i = 0; i < parentArray.length(); i++) {

                JSONObject finalObject = parentArray.getJSONObject(i);

                dataArray[i][0] = finalObject.getString("work_ID");
                dataArray[i][1] = finalObject.getString("work_Name");
                dataArray[i][2] = finalObject.getString("work_Type");
                dataArray[i][3] = finalObject.getString("work_Time");
                dataArray[i][4] = finalObject.getString("work_Descrip");
                dataArray[i][5] = finalObject.getString("work_Video");
                dataArray[i][6] = finalObject.getString("equip_ID");

            }

            return dataArray;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
