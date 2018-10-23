package com.devakt.utils;

public class StringUtils {

    private StringUtils() {

    }

    public static boolean equals(String str1, String str2) {

        if(str1 == null) {
            return str2 == null;
        }

        if(str2 == null) {
            return false;
        }

        return str1.equals(str2);

    }

}
