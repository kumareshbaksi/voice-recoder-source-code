package com.example.voicerec;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ImageView start_stop_btn,listbtn;
    Chronometer record_timer;
    TextView current_file;
    Boolean isRecording = false;
    MediaRecorder mediarecoder;
    String recordfile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO},1);
        }

        getSupportActionBar().hide();

        start_stop_btn = findViewById(R.id.recordbtn);
        record_timer = findViewById(R.id.record_timer);
        current_file = findViewById(R.id.current_file);

        start_stop_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if(isRecording){
                    //Stop Recording...
                    start_stop_btn.setImageDrawable(getResources().getDrawable(R.drawable.stop,null));
                    Toast.makeText(MainActivity.this,"File Saved In Android/data/com.example.voicerec",Toast.LENGTH_SHORT).show();
                    current_file.setText("File Saved "+ recordfile);
                    record_timer.setBase(SystemClock.elapsedRealtime());
                    record_timer.stop();
                    mediarecoder.stop();
                    mediarecoder.release();
                    mediarecoder = null;
                    isRecording = false;
                }
                else {
                    // Start recording...
                    //requesting to record acsess...

                        record_timer.start();
                        // Changing Button In Pause...
                        start_stop_btn.setImageDrawable(getResources().getDrawable(R.drawable.record,null));

                        // Saving file to memory...

                        String recordPath = getExternalFilesDir("/").getAbsolutePath();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_mm_dd_hh_mm_ss", Locale.CANADA);
                        Date now = new Date();
                        recordfile = " "+formatter.format(now)+".mp3";
                        current_file.setText("Recording File "+recordfile);

                        // Recording Audio (Followed By Android Studio Documantation...)

                        mediarecoder = new MediaRecorder();
                        mediarecoder.setAudioSource(MediaRecorder.AudioSource.MIC);
                        mediarecoder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                        mediarecoder.setOutputFile(recordPath+ "/"+recordfile);
                        mediarecoder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

                        try {
                            mediarecoder.prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mediarecoder.start();
                        isRecording = true;

                }

            }
        });






    }
}



//    private boolean checkPermission(Context ctx) {
//
//        if(ActivityCompat.checkSelfPermission(ctx,Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED){
//            return true;
//        }
//        else{
//
//            ActivityCompat.requestPermissions((Activity) ctx ,new String[]{Manifest.permission.RECORD_AUDIO},21);
//            return false;
//
//        }
//    }

