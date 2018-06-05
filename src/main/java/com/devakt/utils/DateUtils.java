package com.devakt.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils {

    private DateUtils () {

    }

    private static SimpleDateFormat SDF_HH_MM = new SimpleDateFormat("HH:mm");

    public static int dateToInterval(String date) {

        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(SDF_HH_MM.parse(date));
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            int minutes = cal.get(Calendar.MINUTE);
            return hour * 4 + minutes / 15;
        } catch (ParseException e) {
            return -1;
        }

    }

    public static String intervalToDate(int interval) {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, interval / 4);
        cal.set(Calendar.MINUTE, 15 * (interval % 4));
        return SDF_HH_MM.format(cal.getTime());

    }

}
