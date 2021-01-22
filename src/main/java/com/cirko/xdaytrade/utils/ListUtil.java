package com.cirko.xdaytrade.utils;

public class ListUtil {
    public static String[] concat(String[]... arrays) {
        int length = 0;
        for (String[] array : arrays) {
            length += array.length;
        }
        String[] newArray = new String[length];
        int pos = 0;
        for (String[] array : arrays) {
            System.arraycopy(array, 0, newArray, pos, array.length);
            pos += array.length;
        }
        return newArray;
    }
}
