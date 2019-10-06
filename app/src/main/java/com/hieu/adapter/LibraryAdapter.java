package com.hieu.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.hieu.R;
import com.hieu.activity.DetailAudioActivity;
import com.hieu.utils.CommonUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.ViewHolder> {
    private Context context;
    private List<File> audioList;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private MediaPlayer mediaPlayer;


    public LibraryAdapter(Context context, List<File> audioList) {
        this.context = context;
        this.audioList = audioList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_library, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final File file = audioList.get(position);
        final long size = file.length() / 1024;
        final String dateFile = dateFormat.format(file.lastModified());
        mediaPlayer = MediaPlayer.create(context, Uri.parse(file.getPath()));

        holder.tv_name_item_audio.setText(file.getName());
        holder.tv_size_item_audio.setText(String.valueOf(size) + " kb" + " | "+CommonUtils.formatTime(mediaPlayer.getDuration()));
        holder.tv_time_item_audio.setText(dateFile);
        holder.view_item_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailAudioActivity.class);
                intent.putExtra("path", file.getPath());
                intent.putExtra("name", file.getName());
                intent.putExtra("size", String.valueOf(size));
                intent.putExtra("date", dateFile);
                intent.putExtra("position",position);
                intent.putExtra("duration",CommonUtils.formatTime(mediaPlayer.getDuration()));

                context.startActivity(intent);
            }
        });
        holder.iv_setting_item_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PopupMenu popup = new PopupMenu(context, v);
//                MenuInflater inflater = popup.getMenuInflater();
//                inflater.inflate(R.menu.popup_menu, popup.getMenu());
//                popup.show();

                /*share*/
//                Intent shareIntent = new Intent();
//                shareIntent.setAction(Intent.ACTION_SEND);
//                shareIntent.putExtra(Intent.EXTRA_STREAM, file.getPath());
//                shareIntent.setType("audio/mpeg");
//                context.startActivity(Intent.createChooser(shareIntent, null));

                /*delete*/
//                new File(file.getPath()).delete();
                /*rename*/
//                String nameOldFile = file.getName();
//                File file = new File(nameOldFile);
//                File file2 = new File("Co gai m52");
//                boolean success = file.renameTo(file2);
//                if (success){
//                    Toast.makeText(context, "thanh cong", Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(context, "false", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return audioList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name_item_audio;
        private TextView tv_time_item_audio;
        private TextView tv_size_item_audio;
        private ConstraintLayout view_item_audio;
        private FrameLayout iv_setting_item_audio;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name_item_audio = itemView.findViewById(R.id.tv_name_item_audio);
            tv_size_item_audio = itemView.findViewById(R.id.tv_size_item_audio);
            tv_time_item_audio = itemView.findViewById(R.id.tv_time_item_audio);
            view_item_audio = itemView.findViewById(R.id.view_item_audio);
            iv_setting_item_audio = itemView.findViewById(R.id.iv_setting_item_audio);
        }
    }
}
