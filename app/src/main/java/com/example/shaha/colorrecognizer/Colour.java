package com.example.shaha.colorrecognizer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This class hold the data of the color, his ral name
 * and his Hex and RGB value
 * Created by shaha on 04/09/2017.
 */

public class Colour implements Parcelable {
    String ral;
    String hexCode;
    String engName;
    int r;
    int g;
    int b;

    public Colour(String ral, String hexCode, String engName, int r, int g, int b) {
        this.ral = ral;
        this.hexCode = hexCode;
        this.engName = engName;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    protected Colour(Parcel in) {
        ral = in.readString();
        hexCode = in.readString();
        engName = in.readString();
        r = in.readInt();
        g = in.readInt();
        b = in.readInt();
    }

    public static final Creator<Colour> CREATOR = new Creator<Colour>() {
        @Override
        public Colour createFromParcel(Parcel in) {
            return new Colour(in);
        }

        @Override
        public Colour[] newArray(int size) {
            return new Colour[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ral);
        dest.writeString(hexCode);
        dest.writeString(engName);
        dest.writeInt(r);
        dest.writeInt(g);
        dest.writeInt(b);
    }

    //getters
    public String getRal() {
        return ral;
    }

    public String getEngName() {
        return engName;
    }

    public int getR(){
        return r;
    }

    public int getG(){
        return g;
    }

    public int getB(){
        return b;
    }
}
