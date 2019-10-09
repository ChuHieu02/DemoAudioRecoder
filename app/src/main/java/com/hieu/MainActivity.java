package com.hieu;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.hieu.activity.LibraryActivity;
import com.hieu.activity.SettingsActivity;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivBottomLibrary;
    private ImageView ivBottomRecoder;
    private ImageView ivBottomSettings;
    private MediaRecorder mAudioRecorder;
    private ImageView ivRecord , ivPauseResume , iv_bg;
    private String outputFile;
    private int recordingStatus;
    private int pauseStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mappingBottomNavigation();
        createFile();
        recordingStatus = 0;
        pauseStatus = 0;
        onRecordAudio();

    }

    private void mappingBottomNavigation() {

        ivBottomLibrary =  findViewById(R.id.iv_bottom_library);
        ivBottomRecoder =  findViewById(R.id.iv_bottom_recoder);
        ivBottomSettings =  findViewById(R.id.iv_bottom_settings);

        iv_bg =  findViewById(R.id.iv_bg);
        ivPauseResume = findViewById(R.id.imageViewPauseResume);
        ivRecord = findViewById(R.id.imageViewRecord);


        ivPauseResume.setEnabled(false);

        ivBottomSettings.setOnClickListener(this);
        ivBottomLibrary.setOnClickListener(this);

    }



    private void createFile() {
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "Recorder");
        outputFile ="/"+ file.getAbsolutePath()+"/RecordFile"+System.currentTimeMillis()+".wav";
        if (!file.exists()) {
            file.mkdirs();
        }

    }
    private int isEnable(){
        ivPauseResume.setEnabled(true);
        recordingStatus = 1;
        return recordingStatus;
    }

    private int isDisable(){
        ivPauseResume.setEnabled(false);
        recordingStatus = 0;
        return  recordingStatus;

    }

    private  void  setupMediaRecorder(){
        mAudioRecorder = new MediaRecorder();
        mAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mAudioRecorder.setOutputFile(outputFile);
    }

    private  void startRecording(){
        createFile();
        // outputFile =Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator+"Test" + ".mp3";
        setupMediaRecorder();
        try {
            mAudioRecorder.prepare();
            mAudioRecorder.start();
        } catch (IllegalStateException ise) {
            // make something ...
        } catch (IOException ioe) {
            // make something
        }
        Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void pauseRecording(){
        if (mAudioRecorder!=null){
            mAudioRecorder.pause();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void resumeRecording(){
        if (mAudioRecorder!=null){
            mAudioRecorder.resume();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void stopRecording(){
        try {
            if (mAudioRecorder!=null ){
                mAudioRecorder.stop();
                mAudioRecorder.release();
                mAudioRecorder = null;
            }
        }catch (Exception e){

        }
    }

    private void playRecodingResult(){
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(outputFile);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            // make something
        }
    }

    private  void onRecordAudio(){
        ivRecord.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if(recordingStatus == 0){
                    startRecording();
                    ivRecord.setImageResource(R.drawable.ic_play_record_pr);
                    recordingStatus =1;
                    pauseStatus = 0;
                    ivPauseResume.setImageResource(R.drawable.ic_home_pause);
                    ivPauseResume.setEnabled(true);
                    //isEnable();
                }else {
                    if(recordingStatus == 1){
                        stopRecording();
                        ivRecord.setImageResource(R.drawable.ic_home_record);
                        recordingStatus =0;
                        pauseStatus = 0;
                        ivPauseResume.setImageResource(R.drawable.ic_home_pause);
                        ivPauseResume.setEnabled(false);
                    }
                }
            }
        });
        ivPauseResume.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if(pauseStatus == 0) {
                    pauseRecording();
                    pauseStatus = 1;
                    ivPauseResume.setImageResource(R.drawable.ic_home_play);
                }else{
                    if(pauseStatus == 1){
                        resumeRecording();
                        pauseStatus = 0;
                        ivPauseResume.setImageResource(R.drawable.ic_home_pause);
                    }
                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_bottom_library:
                startActivity(new Intent(this, LibraryActivity.class));
                break;
            case R.id.iv_bottom_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
        }

    }
}