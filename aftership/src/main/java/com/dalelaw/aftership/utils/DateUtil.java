package com.dalelaw.aftership.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by DaleLaw on 22/2/2016.
 */
public class DateUtil {



    public static Date parseDate(String dateStr) throws ParseException {
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        return df2.parse(dateStr);
    }

}
