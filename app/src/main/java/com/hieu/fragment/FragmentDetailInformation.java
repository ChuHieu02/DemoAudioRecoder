package com.hieu.fragment;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hieu.R;
import com.hieu.utils.CommonUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FragmentDetailInformation extends Fragment {
    private TextView tv_name_audio, tv_path_audio, tv_size_audio, tv_time_audio, tv_duration_audio;
    private Bundle bundle;
    private SimpleDateFormat fomatTime = new SimpleDateFormat("mm:ss");
    private File file;
    private MediaPlayer mediaPlayer;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_information, container, false);
        tv_duration_audio = view.findViewById(R.id.tv_duration_audio);

        tv_name_audio =view.findViewById(R.id.tv_name_audio);
        tv_path_audio =view.findViewById(R.id.tv_path_audio);
        tv_size_audio =view.findViewById(R.id.tv_size_audio);
        tv_time_audio =view.findViewById(R.id.tv_time_audio);


        return view;
    }

    private void mappingTV() {


    }
}
