package com.hieu.activity;

import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
    private MediaPlayer mediaPlayer;
    private int position;
    private ArrayList<File> listFile;
    private SeekBar seekBarDetail;
    private File file;
    private Bundle bundle;
    private Handler handler;
    private Thread thread;
    private SimpleDateFormat fomatTime = new SimpleDateFormat("mm:ss");
    private TextView tv_detail_dration_start, tv_detail_dration_stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_library);

        bundle = getIntent().getExtras();

        listFile = (ArrayList) bundle.getParcelableArrayList("list");
        if (listFile == null) {
            return;
        }
        position = bundle.getInt("position");
        file = listFile.get(position);

        mediaPlayer = MediaPlayer.create(this, Uri.fromFile(listFile.get(position)));
        if (mediaPlayer.getDuration() < 0) {
            return;
        }
        mappingTv();

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int totalDuration = mediaPlayer.getDuration();
                int curenposition = 0;
                while (curenposition < totalDuration) {
                    try {
                        thread.sleep(1000);
                        curenposition = mediaPlayer.getCurrentPosition();
                        seekBarDetail.setProgress(curenposition);
                        tv_detail_dration_start.setText(fomatTime.format(curenposition));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if (mediaPlayer != null) {
                    iv_detail_play_audio.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_circle_filled_black_24dp));

                    mediaPlayer.start();
                    seekBarDetail.setMax(mediaPlayer.getDuration());
                    thread.start();


                }
            }
        });

        if (mediaPlayer == null) {
            Toast.makeText(DetailAudioActivity.this, "Play audio fail !", Toast.LENGTH_SHORT).show();
            return;
        }


        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                iv_detail_play_audio.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_arrow_black_24dp));

            }
        });

        seekBarDetail.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBarDetail.getProgress());
            }
        });


        iv_detail_play_audio.setOnClickListener(this);
        iv_detail_next1_audio.setOnClickListener(this);
        iv_detail_next2_audio.setOnClickListener(this);
        iv_detail_prev1_audio.setOnClickListener(this);
        iv_detail_prev2_audio.setOnClickListener(this);

    }



    private void mappingTv() {
        seekBarDetail = findViewById(R.id.seekBar_detail);

        iv_detail_play_audio = findViewById(R.id.iv_detail_play_audio);
        iv_detail_next1_audio = findViewById(R.id.iv_detail_next1_audio);
        iv_detail_next2_audio = findViewById(R.id.iv_detail_next2_audio);
        iv_detail_prev1_audio = findViewById(R.id.iv_detail_prev1_audio);
        iv_detail_prev2_audio = findViewById(R.id.iv_detail_prev2_audio);

        tv_duration_audio = findViewById(R.id.tv_duration_audio);
        tv_name_audio = findViewById(R.id.tv_name_audio);
        tv_path_audio = findViewById(R.id.tv_path_audio);
        tv_size_audio = findViewById(R.id.tv_size_audio);
        tv_time_audio = findViewById(R.id.tv_time_audio);

        tv_detail_dration_start = findViewById(R.id.tv_detail_dration_start);
        tv_detail_dration_stop = findViewById(R.id.tv_detail_dration_stop);

        tv_detail_dration_stop.setText(fomatTime.format(mediaPlayer.getDuration()));


        tv_name_audio.setText(file.getName());
        tv_size_audio.setText(CommonUtils.fomatSize(file.length()));
        tv_path_audio.setText(file.getAbsolutePath());
        tv_time_audio.setText(CommonUtils.fomatDate(file.lastModified()));
        tv_duration_audio.setText(fomatTime.format(mediaPlayer.getDuration()));


    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_detail_play_audio:
                mediaPlayer.seekTo(seekBarDetail.getProgress());

                if (mediaPlayer.isPlaying()) {
                    iv_detail_play_audio.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_arrow_black_24dp));
                    mediaPlayer.pause();
                } else {
                    iv_detail_play_audio.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_circle_filled_black_24dp));
                    mediaPlayer.start();
                }
                break;

            case R.id.iv_detail_next2_audio:
                position++;
                if (position > listFile.size() - 1) {
                    position = 0;
                }

                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
                mediaPlayer = MediaPlayer.create(this, Uri.fromFile(listFile.get(position)));
                if (mediaPlayer != null) {
                    mediaPlayer.start();
                }
                if (mediaPlayer == null) {
                    Toast.makeText(DetailAudioActivity.this, "Play audio fail !", Toast.LENGTH_SHORT).show();
                    return;
                }

                tv_name_audio.setText(listFile.get(position).getName());
                tv_path_audio.setText(listFile.get(position).getPath());
                tv_size_audio.setText(CommonUtils.fomatSize(listFile.get(position).length()));
                tv_time_audio.setText(CommonUtils.fomatDate(listFile.get(position).lastModified()));
                tv_duration_audio.setText(CommonUtils.formatTime(mediaPlayer.getDuration()));
                tv_detail_dration_stop.setText(fomatTime.format(mediaPlayer.getDuration()));

                iv_detail_play_audio.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_circle_filled_black_24dp));
                break;

            case R.id.iv_detail_prev2_audio:
                position--;
                if (position < 0) {
                    position = listFile.size() - 1;
                }
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
                mediaPlayer = MediaPlayer.create(this, Uri.fromFile(listFile.get(position)));
                if (mediaPlayer != null) {
                    mediaPlayer.start();
                }
                if (mediaPlayer == null) {
                    Toast.makeText(DetailAudioActivity.this, "Play audio fail !", Toast.LENGTH_SHORT).show();
                    return;
                }

                tv_name_audio.setText(listFile.get(position).getName());
                tv_path_audio.setText(listFile.get(position).getPath());
                tv_size_audio.setText(CommonUtils.fomatSize(listFile.get(position).length()));
                tv_time_audio.setText(CommonUtils.fomatDate(listFile.get(position).lastModified()));
                tv_duration_audio.setText(CommonUtils.formatTime(mediaPlayer.getDuration()));
                tv_detail_dration_stop.setText(fomatTime.format(mediaPlayer.getDuration()));

                iv_detail_play_audio.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_circle_filled_black_24dp));
                break;
//
//            case R.id.iv_detail_next1_audio:
//                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()+5000);
//                break;
//            case R.id.iv_detail_prev1_audio:
//                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()-5000);
//                break;
        }
    }


}

