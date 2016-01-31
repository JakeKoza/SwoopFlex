package com.unf.swoopflex;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;


/**
 * Created by Jake on 1/21/16.
 */
public class Tracking extends Fragment {
    private LineGraphSeries<DataPoint> mSeries1;
    private double graph2LastValue = 5d;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tracking, container, false);
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

}
