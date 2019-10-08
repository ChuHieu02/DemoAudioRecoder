package com.hieu.utils;

import android.media.MediaMetadataRetriever;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class CommonUtils {

    private String durationRespon;
    private String duration;

    public static String fomatDate(long date) {
        String dateRespon = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateRespon = dateFormat.format(date);
        return dateRespon;
    }

    public static String fomatSize(long size) {
        String sizeRespon = "";
        sizeRespon = String.valueOf(size / 1024) + " kb";
        return sizeRespon;
    }

    public static String formatToNumber(String data) {
        NumberFormat numberFormat = new DecimalFormat("###,###,###");
        return numberFormat.format(Long.parseLong(data));
    }

    public static String formatTime(long miliseconds) {
        String finaltimeSting = "";
        String timeSecond;

        int hourse = (int) (miliseconds / (1000 * 60 * 60));
        int minutes = (int) (miliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) (miliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000;

        if (hourse > 0) {
            finaltimeSting = hourse + " : ";
        }
        if (seconds < 10) {
            timeSecond = "0" + seconds;

        } else {
            timeSecond = "" + seconds;
        }
        finaltimeSting = finaltimeSting + minutes + ":" + timeSecond;
        return finaltimeSting;
    }

    public static String GetDuration(String path) {
        String durationRespon = "";
        String duration;
        try {
            MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();

            metaRetriever.setDataSource(path);
            duration = metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            long dur = Long.parseLong(duration);

            metaRetriever.release();
            durationRespon = formatTime(dur);

        } catch (Exception e) {

        }
        return durationRespon;
    }

}
