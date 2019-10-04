package com.hieu;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView rvListAudio;

    private File file;
    private String[] itemAudio;
    private ArrayList songList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MediaPlayer mediaPlayer = new MediaPlayer();
        createFile();

        rvListAudio = findViewById(R.id.rv_list_audio);

        final ArrayList<File> audioSong = readAudio(Environment.getExternalStorageDirectory());
        itemAudio = new String[audioSong.size()];
        for (int i = 0; i < audioSong.size(); i++) {
            itemAudio[i] = audioSong.get(i).getName() + "";
//            itemAudio[i] = audioSong.get(i).getPath() + "";
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemAudio);
        rvListAudio.setAdapter(arrayAdapter);


        rvListAudio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getApplicationContext(), AudioPlayActivity.class).putExtra("position",i).putExtra("list", audioSong));
            }
        });
    }

    private void createFile() {
        file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Recorder");
        if (!file.exists()) {
            file.mkdirs();
        }
    }


    public ArrayList<File> readAudio(File file) {
        ArrayList<File> arrayList = new ArrayList<>();


        File[] files = file.listFiles();

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
//        final MediaPlayer mediaPlayer = new MediaPlayer();
//        File[] files = file.listFiles();
//
//        for (File file1 : files) {
//            if (file1.getPath().toLowerCase().endsWith(".mp3")) {
//                try {
////                    mediaPlayer.setDataSource(file1.getAbsolutePath());
////                    mediaPlayer.prepare();
////                    mediaPlayer.start();
//
//                } catch (Exception ex) {
//
//                }
//                break;
//            }
//        }

    }



}
