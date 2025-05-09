package com.codewithdemis.components;

import java.awt.*;
import java.util.Map;

public class VariantColor {
    private static final Map<String, Color[]> VARIANT_COLORS = Map.of(
            "primary", new Color[]{new Color(206, 212, 218), new Color(0, 123, 255)},
            "danger", new Color[]{new Color(220, 53, 69), new Color(200, 35, 51)},
            "success", new Color[]{new Color(40, 167, 69), new Color(30, 140, 60)},
            "warning", new Color[]{new Color(255, 193, 7), new Color(255, 174, 0)},
            "secondary", new Color[]{new Color(108, 117, 125), new Color(73, 80, 87)}
    );
    public static Map<String,Color[]> getVariantColors(){
        return VARIANT_COLORS;
    }
}
