package com.example.shaha.colorrecognizer;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by shaha on 05/09/2017.
 */

public class ColorAdapter extends ArrayAdapter<Colour> {
    Context mContext;
    ArrayList<Colour> colorsList;

    public ColorAdapter(@NonNull Context context, ArrayList<Colour> colorsList) {
        super(context, 0, colorsList);
        mContext = context;
        this.colorsList = colorsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.color_item, parent, false);
        }
        //get the colour object located in that location
        Colour curColor = getItem(position);

        //Find the colorPreview and change it to the curColor
        ColorPreview colorPreview = (ColorPreview)listItemView.findViewById(R.id.colorPrev);
        colorPreview.setColor(curColor.getR(),curColor.getG(),curColor.getB());

        //Find the text views and change them to the curColor
        TextView engNameTxt = (TextView)listItemView.findViewById(R.id.colorEngNameTxt);
        engNameTxt.setText(curColor.getEngName());

        TextView ralNameTxt = (TextView)listItemView.findViewById(R.id.colorRalTxt);
        ralNameTxt.setText(curColor.getRal());

        return listItemView;
    }
}