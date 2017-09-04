package com.example.shaha.colorrecognizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
     * @param r
     * @param g
     * @param b
     * @return
     */
    public ArrayList<Colour> getSimilarColors(int r, int g, int b) {
        return null;
    }
}
