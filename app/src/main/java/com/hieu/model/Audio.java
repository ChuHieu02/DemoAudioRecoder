package com.hieu.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Audio implements Parcelable {
    private String name;
    private String path;
    private String date;
    private String duration;
    private String size;



    public Audio(String name, String path, String date, String duration, String size) {
        this.name = name;
        this.path = path;
        this.date = date;
        this.duration = duration;
        this.size = size;
    }


    protected Audio(Parcel in) {
        name = in.readString();
        path = in.readString();
        date = in.readString();
        duration = in.readString();
        size = in.readString();
    }

    public static final Creator<Audio> CREATOR = new Creator<Audio>() {
        @Override
        public Audio createFromParcel(Parcel in) {
            return new Audio(in);
        }

        @Override
        public Audio[] newArray(int size) {
            return new Audio[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(path);
        dest.writeString(date);
        dest.writeString(duration);
        dest.writeString(size);
    }



}
