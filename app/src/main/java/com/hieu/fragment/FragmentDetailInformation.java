package com.hieu.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hieu.R;
import com.hieu.model.Audio;

import java.util.ArrayList;

public class FragmentDetailInformation extends Fragment {
    private TextView tv_name_audio, tv_path_audio, tv_size_audio, tv_time_audio, tv_duration_audio;
    private Audio audio;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_information, container, false);

        Bundle arguments = getArguments();
        if (arguments != null){
            this.audio = arguments.getParcelable("audio");
        }

        tv_name_audio = view.findViewById(R.id.tv_name_audio);
        tv_duration_audio = view.findViewById(R.id.tv_duration_audio);
        tv_path_audio = view.findViewById(R.id.tv_path_audio);
        tv_size_audio = view.findViewById(R.id.tv_size_audio);
        tv_time_audio = view.findViewById(R.id.tv_time_audio);

        tv_name_audio.setText(audio.getName());
        tv_duration_audio.setText(audio.getDuration());
        tv_path_audio.setText(audio.getPath());
        tv_size_audio.setText(audio.getSize());
        tv_time_audio.setText(audio.getDate());

        return view;
    }


    public FragmentDetailInformation setArguments(Audio audio) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("audio", audio);
        setArguments(bundle);
        return this;
    }

    public void updateFragInfor(Audio audio) {
        tv_name_audio.setText(audio.getName());
        tv_duration_audio.setText(audio.getDuration());
        tv_path_audio.setText(audio.getPath());
        tv_size_audio.setText(audio.getSize());
        tv_time_audio.setText(audio.getDate());
    }


}
