package com.example.shaha.colorrecognizer;

/**
 * This class hold the data of the color, his ral name
 * and his Hex and RGB value
 * Created by shaha on 04/09/2017.
 */

public class Colour {
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
