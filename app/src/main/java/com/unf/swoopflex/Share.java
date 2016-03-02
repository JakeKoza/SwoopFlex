package com.unf.swoopflex;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Jake on 1/21/16.
 */
public class Share extends Fragment {
    Button qBt = null;
    EditText qText = null;
    TextView qView = null;
    String qData = "";

    public Share(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.share, container, false);

        qText = (EditText)view.findViewById(R.id.query_edit);
        qView = (TextView)view.findViewById(R.id.text_query);
        qBt = (Button)view.findViewById(R.id.query_button);

        qBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                qData = qText.getText().toString();
                Toast.makeText(getActivity(), "Query Data =" + qData, Toast.LENGTH_LONG).show();
                new JsonTask().execute("test");

            }

        });
                return view;
    }

    private class JsonTask extends AsyncTask<String,String,String> {

        HttpConnect http = new HttpConnect();
        String[][] dataArray = null;

        @Override
        protected String doInBackground(String... params) {
            String queryData = null;
            queryData = http.GetWorkoutByEquipId(qData);

            return queryData;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            dataArray = http.jsonWorkoutStringArrayParser(result);

            qView.setText(String.valueOf(dataArray[0][4]));

        }

    }
}
