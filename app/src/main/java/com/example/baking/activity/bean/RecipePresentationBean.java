package com.example.baking.activity.bean;

import android.os.Parcel;
import android.os.Parcelable;
import lombok.Data;

@Data
public class RecipePresentationBean implements Parcelable {
    private String name;
    public RecipePresentationBean(){

    }

    protected RecipePresentationBean(Parcel in) {
        name = in.readString();
    }

    public static final Creator<RecipePresentationBean> CREATOR = new Creator<RecipePresentationBean>() {
        @Override
        public RecipePresentationBean createFromParcel(Parcel in) {
            return new RecipePresentationBean(in);
        }

        @Override
        public RecipePresentationBean[] newArray(int size) {
            return new RecipePresentationBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
    }
}
