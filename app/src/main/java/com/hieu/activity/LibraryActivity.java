package com.hieu.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import com.hieu.R;
import com.hieu.adapter.LibraryAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LibraryActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView rvLibrary;
    private LibraryAdapter adapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        checkPerimison();
        mappingToolbar();
        mappingRecyclerView();

        final ArrayList<File> audioSong = readAudio(new File(Environment.getExternalStorageDirectory() + File.separator + "Recorder"));
        layoutManager = new LinearLayoutManager(this);
        rvLibrary.setLayoutManager(layoutManager);
        adapter = new LibraryAdapter(this, audioSong);


        rvLibrary.setAdapter(adapter);
    }

    private void checkPerimison() {

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
}
