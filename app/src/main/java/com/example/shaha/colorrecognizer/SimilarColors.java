package com.example.shaha.colorrecognizer;

import android.graphics.Color;
import android.support.v4.graphics.ColorUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by shaha on 04/09/2017.
 */

public class SimilarColors {
    private Map<String, Colour> colorsDict;

    public SimilarColors(Map<String, Colour> colorsDict) {
        this.colorsDict = colorsDict;
    }

    /**
     * perform a color different algorithm between all the stored colors to the given one
     *
     * @param r
     * @param g
     * @param b
     * @return
     */
    public TreeMap<Colour, Double> getSimilarColors(int r, int g, int b) {
        //convert the color from rgb to lab representation.
        double[] outLab = new double[3];
        ColorUtils.RGBToLAB(r, g, b, outLab);
        //prepare the settings for the color comparision
        double[] lab2 = new double[3];
        HashMap<Colour, Double> colorRank = new HashMap<>();
        //iterate on each color in the dictionary and check the distance
        for (Colour color : colorsDict.values()) {
            //convert the color from rgb to lab
            ColorUtils.RGBToLAB(color.getR(), color.getG(), color.getB(), lab2);
            //compare outLab with lab2
            double dist = compareColors(outLab, lab2);
            colorRank.put(color, dist);
        }
        //get the top5 and add them to the ArrayList
        //1. add the first five colors to the temp dictionary
        int i = 0;
        HashMap<Colour, Double> top5Colors = new HashMap<>();
        double max = 0;
        Colour maxColor = null;
        for (Colour color : colorRank.keySet()) {
            double dist = colorRank.get(color);
            if (i < 5) {
                if (dist > max) {
                    max = dist;
                    maxColor = color;
                }
                top5Colors.put(color, dist);
                i++;
            } else {
                if (dist < max) {
                    //enter the color to the top list
                    top5Colors.remove(maxColor);
                    top5Colors.put(color, dist);
                    max = dist;
                    maxColor = color;
                }
            }
        }
        //sort the colors from the most similar to the less similar
        ValueComparator bvc = new ValueComparator(top5Colors);
        TreeMap<Colour, Double> sorted_map = new TreeMap<Colour, Double>(bvc);
        sorted_map.putAll(top5Colors);

        return sorted_map;
    }

    class ValueComparator implements Comparator<Colour> {
        Map<Colour, Double> base;

        public ValueComparator(Map<Colour, Double> base) {
            this.base = base;
        }

        // Note: this comparator imposes orderings that are inconsistent with
        // equals.
        public int compare(Colour a, Colour b) {
            if (base.get(a) >= base.get(b)) {
                return 1;
            } else {
                return -1;
            } // returning 0 would merge keys
        }
    }

    /**
     * Compare the colors with LAB Delta E algorithm
     *
     * @param lab1 [0] = l [1] = a [2] = b
     * @param lab2
     * @return
     */
    private double compareColors(double[] lab1, double[] lab2) {
        //calculate the euclidean distance
        double sum = Math.pow(lab2[0] - lab1[0], 2) + Math.pow(lab2[1] - lab1[1], 2) + Math.pow(lab2[2] - lab1[2], 2);
        return Math.sqrt(sum);
    }
}
