package com.example.bryce.twitter_api_app;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.Calendar;
import java.util.Date;

public class Time {

    private static enum months {
        Jan,Feb,Mar,Apr,May,Jun,Jul,Aug,Sept,Oct,Nov,Dec
    };

    /*
    *
    * Description: Gets the current timestamp in the form of day month. e.g. 1 Jan
    * Return: String representing the day and month.
    *
    * */
    public static String GetTimeStamp()
    {
        Calendar currentTime = Calendar.getInstance();

        return Integer.toString(currentTime.get(Calendar.DAY_OF_MONTH)) + " " + months.values()[currentTime.get(Calendar.MONTH)];
    }


}
