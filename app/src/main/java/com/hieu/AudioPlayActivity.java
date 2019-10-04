package com.hieu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class AudioPlayActivity extends AppCompatActivity {
    private TextView tvAudioSong;
    private Button btPauseAudio;
    private Bundle bundle;
    MediaPlayer mediaPlayer;
    private Button btPlayAudio;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_play);
        tvAudioSong = (TextView) findViewById(R.id.tv_audioSong);
        btPauseAudio = (Button) findViewById(R.id.bt_pause_audio);
        btPlayAudio = (Button) findViewById(R.id.bt_play_audio);


        if (mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        bundle = getIntent().getExtras();

        final ArrayList<File> songs = (ArrayList) bundle.getParcelableArrayList("list");
         final int position = bundle.getInt("position");
        Uri uri = Uri.parse(songs.get(position).toString());

        tvAudioSong.setText(songs.get(position).getName());

            mediaPlayer = mediaPlayer.create(this, uri);
            mediaPlayer.start();



            btPauseAudio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mediaPlayer.pause();
                }
            });
            btPlayAudio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   mediaPlayer.start();

                }
            });
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.pause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mediaPlayer.start();
    }
}
