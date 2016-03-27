package com.unf.swoopflex;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DecimalFormat;
import java.text.NumberFormat;


/**
 * Created by Jake on 1/21/16.
 */
public class Tracking extends Fragment implements View.OnClickListener{
    private LineGraphSeries<DataPoint> mSeries1;
    private double graph2LastValue = 5d;
    Button btnGoogle, btnFacebook, btnTwitter;
    Context ctx = null;
    TextView BMI;
    Double bmi;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tracking, container, false);

        //Creates DB Object
        ctx = getActivity().getApplicationContext();
        SQLiteDB DB = new SQLiteDB(ctx);
        //Checks to see if DB info exist. If true display bmi
        if(DB.SQLiteCheckDB(DB)) {
            BMI = (TextView) view.findViewById(R.id.tvBMI);
            Cursor CR = DB.SQLiteUserGetBMI(DB);
            CR.moveToFirst();
            bmi = CR.getDouble(0);
            NumberFormat formatter = new DecimalFormat("#0.00");
            BMI.setText(String.valueOf(formatter.format(bmi)));
        }

        GraphView graph = (GraphView) view.findViewById(R.id.graph);
        mSeries1 = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(0,256),
                new DataPoint(1,245),
                new DataPoint(2,300),
                new DataPoint(3,206),
                new DataPoint(4,250)
        });
        graph.addSeries(mSeries1);
        mSeries1.setColor(Color.rgb(25, 118, 210));
        mSeries1.setDrawDataPoints(true);
        mSeries1.setDataPointsRadius(5);
        mSeries1.setThickness(3);

        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
