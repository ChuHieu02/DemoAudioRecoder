package com.hieu.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.widget.Toast;

import com.hieu.MainActivity;
import com.hieu.R;
import com.hieu.adapter.LibraryAdapter;
import com.hieu.model.Audio;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class LibraryActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView rvLibrary;
    private LibraryAdapter adapter;
    private LinearLayoutManager layoutManager;
    private List<Audio> audioList = new ArrayList<>();
    private SimpleDateFormat timeformat = new SimpleDateFormat("mm:ss");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private MediaPlayer mediaPlayer;
    private static final int PERMISSION_REQUEST_CODE = 1000;


    private boolean openAndroidPermissionsWriteSetting() {
        boolean retVal = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            retVal = Settings.System.canWrite(this);
            if (retVal) {
//                Toast.makeText(this, "Write allowed :-)", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + this.getPackageName()));
                startActivity(intent);
//                Toast.makeText(this, "Write not allowed :-(", Toast.LENGTH_LONG).show();
            }
        }
        return retVal;
    }

    private void openAndroidPermissionsWriteStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_CODE);
            }
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        mappingToolbar();

        mappingRecyclerView();

        openAndroidPermissionsWriteSetting();

        openAndroidPermissionsWriteStorage();

        final ArrayList<File> audioSong = readAudio(new File(Environment.getExternalStorageDirectory() + File.separator + "Recorder"));
        for (int i = 0; i < audioSong.size(); i++) {
            File file = audioSong.get(i);
            String path = file.getAbsolutePath();
            String name = file.getName();
            long date = file.lastModified();
            long size = file.length();

            String formatSize = String.valueOf(size / 1024);
            String formatDate = String.valueOf(dateFormat.format(date));

            Audio audio = new Audio(name, path, formatDate, "", formatSize + " kb");
            audioList.add(audio);
        }
        layoutManager = new LinearLayoutManager(this);
        rvLibrary.setLayoutManager(layoutManager);
        adapter = new LibraryAdapter(this, audioList);
        rvLibrary.setAdapter(adapter);
    }


    private void mappingRecyclerView() {
        rvLibrary = (RecyclerView) findViewById(R.id.rv_library);
        rvLibrary.setHasFixedSize(true);
    }

    private void mappingToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Library");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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
                if (invidualFile.getName().endsWith(".mp3") || invidualFile.getName().endsWith(".wav") || invidualFile.getName().endsWith(".wma")) {
                    arrayList.add(invidualFile);
                }
            }
        }
        return arrayList;
    }

    private void getAudio() {
        ContentResolver cr = getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cur = cr.query(uri, null, null, null, null);
        int count = 0;
        if (cur != null) {
            count = cur.getCount();
            if (count > 0) {
                while (cur.moveToNext()) {
                    String data = cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.DATA));
                    String duration = cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.DURATION));
                    String name = cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.TITLE));
                    String date = cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.DATE_MODIFIED));
                    String size = cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.SIZE));

                    String fomartDate = dateFormat.format(Long.parseLong(date));
                    String formatTime = timeformat.format(Long.parseLong(duration));
                    int formatSize = Integer.parseInt(size) / 1024;

//                    Audio audio = new Audio(name, data, fomartDate, formatTime, formatSize);
//                    audioList.add(audio);
                }
            }
        }
        if (cur == null) {
            return;
        }
        cur.close();
    }
}
