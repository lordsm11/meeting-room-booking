package com.devakt.utils;

public class IntegerUtils {

    private IntegerUtils() {

    }

    public static boolean equals(Integer integer1, Integer integer2) {

        if (integer1 == null) {
            return integer2 == null;
        }

        return integer2 != null && integer1.intValue() == integer2.intValue();

    }

}
