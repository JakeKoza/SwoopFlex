package com.unf.swoopflex;


import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
import android.net.Uri;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * Created by Jake on 1/21/16.
 */
public class RandomWorkout extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_workout);
    }
    //public View onCreateView(LayoutInflater inflater, ViewGroup container,
  //                           Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       /* VideoView videoView =(VideoView)findViewById(R.id.videoView);
        MediaController mediaController= new MediaController(this);
        mediaController.setAnchorView(videoView);
        Uri uri=Uri.parse("rtsp://r7---sn-4g57kue6.googlevideo.com/Ck0LENy73wIaRAmk3cJBg-iaXhMYDSANFC3u0pRWMOCoAUIJbXYtZ29vZ2xlSARSBXdhdGNoYIaluaTkzciOVooBCzVxRjNraG5XcXdnDA==/D693A8E7577C3A29E60C292B42C9C87D7C25A565.762A63DC4CA0A028DA83256C6A79E5F160CBEDA3/yt6/1/video.3gp");
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();
*/



        //return inflater.inflate(R.layout.display_workout, container, false);
    }

