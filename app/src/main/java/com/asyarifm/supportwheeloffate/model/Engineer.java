package com.asyarifm.supportwheeloffate.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Engineer implements Parcelable {
    private int id;
    private String name;
    private int workload;

    public Engineer(int id, String name) {
        this.id = id;
        this.name = name;
        this.workload = 0;
    }

    protected Engineer(Parcel in) {
        id = in.readInt();
        name = in.readString();
        workload = in.readInt();
    }

    public static final Creator<Engineer> CREATOR = new Creator<Engineer>() {
        @Override
        public Engineer createFromParcel(Parcel in) {
            return new Engineer(in);
        }

        @Override
        public Engineer[] newArray(int size) {
            return new Engineer[size];
        }
    };

    public int getWorkload() {
        return workload;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void increaseWorkload() {
        workload += 1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeInt(workload);
    }
}
