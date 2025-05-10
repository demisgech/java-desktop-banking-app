package com.codewithdemis.components;

import java.awt.*;
import java.util.Map;

public class VariantColor {
    private static final Map<String, Color[]> VARIANT_COLORS = Map.of(
            "primary", new Color[]{new Color(30, 144, 255), new Color(0, 105, 217), new Color(0, 90, 190), new Color(0, 78, 175)},
            "danger", new Color[]{new Color(220, 53, 69), new Color(200, 35, 51), new Color(180, 30, 45), new Color(140, 30, 40)},
            "success", new Color[]{new Color(40, 167, 69), new Color(30, 140, 60), new Color(20, 120, 50), new Color(20, 100, 40)},
            "warning", new Color[]{new Color(255, 193, 7), new Color(255, 174, 0), new Color(240, 160, 0), new Color(210, 140, 0)},
            "secondary", new Color[]{new Color(108, 117, 125), new Color(92, 104, 112), new Color(78, 90, 97), new Color(58, 68, 72)}
    );
    public static Map<String,Color[]> getVariantColors(){
        return VARIANT_COLORS;
    }
}


