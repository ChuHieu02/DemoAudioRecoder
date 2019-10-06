package com.hieu.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hieu.R;
import com.hieu.utils.CommonUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DetailAudioActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_name_audio, tv_path_audio, tv_size_audio, tv_time_audio, tv_duration_audio;
    private ImageView iv_detail_play_audio, iv_detail_next1_audio, iv_detail_next2_audio, iv_detail_prev1_audio, iv_detail_prev2_audio;
    Intent intent;
    MediaPlayer mediaPlayer;
    private int position;
    private ArrayList<File> audioSong;
    private SimpleDateFormat simpleDateFormat= new SimpleDateFormat("mm:ss");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private  SeekBar seekBarDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_audio);

        audioSong = readAudio(new File(Environment.getExternalStorageDirectory() + File.separator + "Recorder"));

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        intent = getIntent();

        mappingTv();
        mediaPlayer = MediaPlayer.create(this, Uri.fromFile(audioSong.get(position)));
//        mediaPlayer = MediaPlayer.create(this, Uri.parse(intent.getStringExtra("path")));
        tv_duration_audio.setText(simpleDateFormat.format(mediaPlayer.getDuration()));

        mediaPlayer.start();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                iv_detail_play_audio.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_play));

            }
        });
        mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mediaPlayer) {

            }
        });
        iv_detail_play_audio.setOnClickListener(this);
        iv_detail_next1_audio.setOnClickListener(this);
        iv_detail_prev1_audio.setOnClickListener(this);

    }

    private void mappingTv() {
        seekBarDetail = (SeekBar) findViewById(R.id.seekBar_detail);

        iv_detail_play_audio = findViewById(R.id.iv_detail_play_audio);
        iv_detail_next1_audio = findViewById(R.id.iv_detail_next1_audio);
        iv_detail_prev1_audio = findViewById(R.id.iv_detail_prev1_audio);
        tv_duration_audio = findViewById(R.id.tv_duration_audio);


        tv_name_audio = (TextView) findViewById(R.id.tv_name_audio);
        tv_path_audio = (TextView) findViewById(R.id.tv_path_audio);
        tv_size_audio = (TextView) findViewById(R.id.tv_size_audio);
        tv_time_audio = (TextView) findViewById(R.id.tv_time_audio);

        tv_name_audio.setText(intent.getStringExtra("name"));
        tv_size_audio.setText(intent.getStringExtra("size") + " kb");
        tv_path_audio.setText(intent.getStringExtra("path"));
        tv_time_audio.setText(intent.getStringExtra("date"));
        position = intent.getIntExtra("position", 0);


    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mediaPlayer.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_detail_play_audio:
                if (mediaPlayer.isPlaying()) {
                    iv_detail_play_audio.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_play));
                    mediaPlayer.pause();
                } else {
                    iv_detail_play_audio.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_pause));
                    mediaPlayer.start();
                }
                break;

            case R.id.iv_detail_next1_audio:
                position++;
                if (position > audioSong.size() - 1) {
                    position = 0;
                }

                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                mediaPlayer = MediaPlayer.create(this, Uri.fromFile(audioSong.get(position)));
                mediaPlayer.start();
                tv_name_audio.setText(audioSong.get(position).getName());
                tv_path_audio.setText(audioSong.get(position).getPath());
                tv_duration_audio.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
                String  date =  dateFormat.format(audioSong.get(position).lastModified());
                tv_time_audio.setText(date);
                iv_detail_play_audio.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_pause));

                break;

            case R.id.iv_detail_prev1_audio:
                position--;
                if (position < 0) {
                    position = audioSong.size() - 1;
                }

                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                mediaPlayer = MediaPlayer.create(this, Uri.fromFile(audioSong.get(position)));
                mediaPlayer.start();
                tv_name_audio.setText(audioSong.get(position).getName());
                tv_path_audio.setText(audioSong.get(position).getPath());
                tv_duration_audio.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
                String  date2 =  dateFormat.format(audioSong.get(position).lastModified());
                tv_time_audio.setText(date2);
                iv_detail_play_audio.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_pause));

                break;
        }
    }

    public ArrayList<File> readAudio(File file) {
        ArrayList<File> arrayList = new ArrayList<>();


        File[] files = file.listFiles();

        if (files == null) {
            return arrayList;
        }
        for (File invidualFile : files) {
            if (invidualFile.isDirectory() && !invidualFile.isHidden()) {
                arrayList.addAll(readAudio(invidualFile));

            } else {
                if (invidualFile.getName().endsWith(".mp3")) {
                    arrayList.add(invidualFile);
                }
            }
        }
        return arrayList;
    }
}

