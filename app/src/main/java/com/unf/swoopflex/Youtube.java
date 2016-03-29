package com.unf.swoopflex;

//import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
//import android.support.v7.widget.ActionBarContextView;
import android.view.View;

/**
 * Created by eldin ikanovic on 3/27/2016.
 */

    public class Youtube extends ActionBarActivity
    {
        public void onCreate (Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.display_workout);
        }
        public void youtube (View v) {
// do something when the button is clicked
            //Button button=(Button) view;
            // View v =  inflater.inflate(R.layout.display_workout);

            Intent youtubeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/"));
            startActivity(youtubeIntent);
        }
    }

