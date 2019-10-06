package com.hieu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.hieu.activity.LibraryActivity;
import com.hieu.activity.SettingsActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private ImageView ivBottomLibrary;
    private ImageView ivBottomRecoder;
    private ImageView ivBottomSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mappingBottomNavigation();


    }

    private void mappingBottomNavigation() {

        ivBottomLibrary = (ImageView) findViewById(R.id.iv_bottom_library);
        ivBottomRecoder = (ImageView) findViewById(R.id.iv_bottom_recoder);
        ivBottomSettings = (ImageView) findViewById(R.id.iv_bottom_settings);

        ivBottomSettings.setOnClickListener(this);
        ivBottomLibrary.setOnClickListener(this);

    }



    private void createFile() {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Recorder");
        if (!file.exists()) {
            file.mkdirs();
        }
    }


    private String formatTime(long miliseconds) {
        String finaltimeSting = "";
        String timeSecond;

        int hourse = (int) (miliseconds / (1000 * 60 * 60));
        int minutes = (int) (miliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) (miliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000;

        if (hourse > 0) {
            finaltimeSting = hourse + ":";
        }
        if (seconds < 10) {
            timeSecond = "0" + seconds;

        } else {
            timeSecond = "" + seconds;
        }
        finaltimeSting = finaltimeSting + minutes + ":" + timeSecond;
        return finaltimeSting;
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