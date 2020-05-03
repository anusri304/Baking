package com.example.baking.bean;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Step implements Parcelable {
    int id;
    String shortDescription;
    @SerializedName("description")
    String instruction;
    String videoURL;
    String thumbnailURL;

    protected Step(Parcel in) {
        id = in.readInt();
        shortDescription = in.readString();
        instruction = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(shortDescription);
        parcel.writeString(instruction);
        parcel.writeString(videoURL);
        parcel.writeString(thumbnailURL);
    }
}
