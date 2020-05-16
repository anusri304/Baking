package com.example.baking.activity.bean;

import android.os.Parcel;
import android.os.Parcelable;
import com.example.baking.bean.Ingredient;
import com.example.baking.bean.Step;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RecipePresentationBean implements Parcelable {
    private int id;
    private String name;
    private String image;
    private String serving;
    List<Ingredient> ingredients = new ArrayList<>();
    List<Step> steps = new ArrayList<>();

    public RecipePresentationBean() {

    }

    protected RecipePresentationBean(Parcel in) {
        id = in.readInt();
        name = in.readString();
        in.readList(ingredients, Ingredient.class.getClassLoader());
        in.readList(steps, Step.class.getClassLoader());
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
        parcel.writeInt(i);
        parcel.writeString(name);
        parcel.writeList(ingredients);
        parcel.writeList(steps);
    }
}
