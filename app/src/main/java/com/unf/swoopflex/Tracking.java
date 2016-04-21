package com.unf.swoopflex;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DecimalFormat;
import java.text.NumberFormat;


/**
 * Class used to display Tracking info
 */
public class Tracking extends Fragment implements View.OnClickListener{

    private LineGraphSeries<DataPoint> mSeries1;
    private double graph2LastValue = 5d;
    private Context ctx = null;
    private TextView BMI;
    private double bmi, RoutineOne, RoutineTwo, RoutineThree, RoutineFour, RoutineFive;

    private CallbackManager callbackManager;
    private ShareDialog shareDialog;
    private Button shareButton, twitterButton, googleButton;
    private Globals g = Globals.getInstance();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getContext());
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tracking, container, false);

        g.setFeature(4);

        GraphView graph = (GraphView) view.findViewById(R.id.graph);

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

            CR = DB.SQLiteTrackingCal(DB);
            CR.moveToLast();

            int temp_Pos = CR.getCount();

            if(temp_Pos >= 5){
                RoutineFive = CR.getDouble(0);
                CR.moveToPrevious();
                RoutineFour = CR.getDouble(0);
                CR.moveToPrevious();
                RoutineThree = CR.getDouble(0);
                CR.moveToPrevious();
                RoutineTwo = CR.getDouble(0);
                CR.moveToPrevious();
                RoutineOne = CR.getDouble(0);

                mSeries1 = new LineGraphSeries<DataPoint>(new DataPoint[]{
                        new DataPoint(0,RoutineOne),
                        new DataPoint(1,RoutineTwo),
                        new DataPoint(2,RoutineThree),
                        new DataPoint(3,RoutineFour),
                        new DataPoint(4,RoutineFive)

                });
            }else{
                mSeries1 = new LineGraphSeries<DataPoint>(new DataPoint[]{
                        new DataPoint(0, 256),
                        new DataPoint(1, 245),
                        new DataPoint(2, 300),
                        new DataPoint(3, 206),
                        new DataPoint(4, 250)
                });

            }

        }else {

            mSeries1 = new LineGraphSeries<DataPoint>(new DataPoint[]{
                    new DataPoint(0, 256),
                    new DataPoint(1, 245),
                    new DataPoint(2, 300),
                    new DataPoint(3, 206),
                    new DataPoint(4, 250)
            });
        }


        graph.addSeries(mSeries1);
        mSeries1.setColor(Color.rgb(25, 118, 210));
        mSeries1.setDrawDataPoints(true);
        mSeries1.setDataPointsRadius(5);
        mSeries1.setThickness(3);

        twitterButton = (Button) view.findViewById(R.id.btnTwitter);
        googleButton = (Button) view.findViewById(R.id.btnGooglePlus);

        twitterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://twitter.com/";

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }});

        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://plus.google.com/";

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }});

        shareButton = (Button) view.findViewById(R.id.btnFacebook);
        shareDialog = new ShareDialog(this);
        callbackManager = CallbackManager.Factory.create();
        shareDialog.registerCallback(callbackManager, new

                FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {}

                    @Override
                    public void onCancel() {}

                    @Override
                    public void onError(FacebookException error) {}
                });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle("Fitness Progress!")
                            .setContentDescription("Over my past five workouts I have burned " +
                                    String.format("%.2f", RoutineOne + RoutineTwo + RoutineThree + RoutineFour + RoutineFive)
                                    + " calories! Im working hard to improve my fitness and so can you!")
                            .setContentUrl(Uri.parse("http://jakekoza13.wix.com/swoopflex"))
                            .build();

                    shareDialog.show(linkContent);
                }
            }});

        return view;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


}
