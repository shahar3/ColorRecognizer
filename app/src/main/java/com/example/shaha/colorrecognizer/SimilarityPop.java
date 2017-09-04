package com.example.shaha.colorrecognizer;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;

/**
 * Created by shaha on 04/09/2017.
 */

public class SimilarityPop extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_popup);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int heigt = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(heigt*.6));
    }
}
