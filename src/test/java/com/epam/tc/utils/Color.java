package com.epam.tc.utils;

public class Color {

    /**
     * color REQUIRED string
     * The color for the label.
     * Valid values: yellow, purple, blue, red, green, orange, black, sky, pink, lime
     */

    public static String randomAvailableColor() {
        String[] myString = new String[]{"yellow", "purple", "blue", "red", "green", "orange", "black", "sky", "pink",
            "lime"};
        int n = (int) Math.floor(Math.random() * myString.length);
        return myString[n];
    }
}
