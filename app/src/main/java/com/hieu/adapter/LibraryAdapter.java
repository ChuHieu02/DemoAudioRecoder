package com.hieu.adapter;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.hieu.model.Audio;
import com.hieu.utils.CommonUtils;

import java.io.File;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.ViewHolder> {
    private Context context;
    private List<Audio> audioList;


    public LibraryAdapter(Context context, List<Audio> audioList) {
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
        MediaPlayer mediaPlayer;
        final Audio audio = audioList.get(position);
        holder.tv_name_item_audio.setText(audio.getName());
        holder.tv_time_item_audio.setText(audio.getDate());
        holder.tv_size_item_audio.setText(audio.getSize() + " | " + audio.getDuration());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailAudioActivity.class);
                intent.putExtra("path", audio.getPath());
                intent.putExtra("name", audio.getName());
                intent.putExtra("size", String.valueOf(audio.getSize()));
                intent.putExtra("date", audio.getDate());
                intent.putExtra("position", position);
                intent.putExtra("duration", audio.getDuration());

                context.startActivity(intent);
            }
        });
        holder.iv_setting_item_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, v);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.pp_delete_item_library:
                                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage("Are you sure to Delete ?")
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                boolean checkDel = new File(audio.getPath()).delete();
                                                if (checkDel) {
                                                    audioList.remove(position);
                                                    notifyDataSetChanged();
                                                    Toast.makeText(context, "Delete success", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(context, "Delete fail", Toast.LENGTH_SHORT).show();

                                                }
                                                notifyDataSetChanged();
                                            }
                                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                builder.create().show();
                                break;
                            case R.id.pp_share_item_library:
//                                Intent shareIntent = new Intent();
//                                shareIntent.setAction(Intent.ACTION_SEND);
//                                shareIntent.setType("audio/*");
//                                shareIntent.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(new File(audio.getPath())));
//                                context.startActivity(Intent.createChooser(shareIntent, audio.getName()));

                                Intent intentShareFile = new Intent(Intent.ACTION_SEND);
                                File fileWithinMyDir = new File(audio.getPath());
                                intentShareFile.setData(Uri.fromFile(fileWithinMyDir));
                                if (fileWithinMyDir.exists()) {
                                    intentShareFile.setType("audio/*");
                                    context.startActivity(Intent.createChooser(intentShareFile, "Open file"));
                                } else {
                                    Toast.makeText(context, "not file ", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case R.id.pp_edit_item_library:
                                File file = new File(audio.getName());
                                File file2 = new File("Co gai m52");
                                boolean success = file.renameTo(file2);
                                if (success) {
                                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case R.id.pp_editContent_item_library:
                                break;
                            case R.id.pp_setRingTone_item_library:

                                ContentValues values = new ContentValues();
                                values.put(MediaStore.MediaColumns.DATA, audio.getPath());
                                values.put(MediaStore.MediaColumns.TITLE, audio.getName());
                                values.put(MediaStore.MediaColumns.SIZE, audio.getDuration());
                                values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/*");
                                values.put(MediaStore.Audio.AudioColumns.ARTIST, context.getString(R.string.app_name));
                                values.put(MediaStore.Audio.AudioColumns.IS_RINGTONE, true);
                                values.put(MediaStore.Audio.AudioColumns.IS_NOTIFICATION, false);
                                values.put(MediaStore.Audio.AudioColumns.IS_ALARM, false);
                                values.put(MediaStore.Audio.AudioColumns.IS_MUSIC, false);

                                Uri uri = MediaStore.Audio.Media.getContentUriForPath(audio.getPath());
                                context.getContentResolver().delete(uri, MediaStore.MediaColumns.DATA + "=\"" + audio.getPath() + "\"", null);

                                Uri newUri = context.getContentResolver().insert(uri, values);

                                //Ok now set the ringtone from the content manager's uri, NOT the file's uri
                                RingtoneManager.setActualDefaultRingtoneUri(context, RingtoneManager.TYPE_RINGTONE, newUri);

                                break;
                        }
                        return false;
                    }
                });
                popup.show();

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

    public void setActualDefaultRingtoneUri(Context context, int type, Uri ringtoneUri) {

    }

    @Override
    public int getItemCount() {
        return audioList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name_item_audio;
        private TextView tv_time_item_audio;
        private TextView tv_size_item_audio;
        private FrameLayout iv_setting_item_audio;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name_item_audio = itemView.findViewById(R.id.tv_name_item_audio);
            tv_size_item_audio = itemView.findViewById(R.id.tv_size_item_audio);
            tv_time_item_audio = itemView.findViewById(R.id.tv_time_item_audio);
            iv_setting_item_audio = itemView.findViewById(R.id.iv_setting_item_audio);
        }
    }
}

