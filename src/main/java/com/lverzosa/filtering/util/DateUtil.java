package com.lverzosa.filtering.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lorenz on 12/13/15.
 */
public class DateUtil {
    private DateUtil() {
    }

    public static final String DATE_FORMAT = "m/d/yyyy HH:mm:ss";
    public static final String DATE_FORMAT_NO_HOUR = "m/d/yyyy";

    public static DateFormat getDateFormat() {
        return new SimpleDateFormat(DATE_FORMAT);
    }

    public static DateFormat getDateFormatNoHour() {
        return new SimpleDateFormat(DATE_FORMAT_NO_HOUR);
    }

    public static Date convert(String value) {
        try {
            return getDateFormat().parse(value);
        } catch (ParseException e) {
            // since there is only one date type (not date and timestamp) the convert method needs to handle both.
            // this would slow down processing because logic is done in exception handling.
            return convertNoHour(value);
        }
    }

    public static Date convertNoHour(String value) {
        try {
            return getDateFormatNoHour().parse(value);
        } catch (ParseException e) {
            // Wrapping parse exception to match rest of api.
            // Should throw parse exception in productionalized code so that usage of converter can assume defaults.
            throw new RuntimeException(e);
        }
    }
}
