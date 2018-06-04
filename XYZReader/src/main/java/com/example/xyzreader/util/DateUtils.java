package com.example.xyzreader.util;

import android.database.Cursor;
import android.util.Log;

import com.example.xyzreader.data.ArticleLoader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public final class DateUtils {

    private static final String TAG = DateUtils.class.getSimpleName();

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss");
    private static final SimpleDateFormat outputFormat = new SimpleDateFormat();
    private static final GregorianCalendar START_OF_EPOCH = new GregorianCalendar(2,1,1);

    public static String getPublishedDate(Cursor cursor) {
        Date publishedDate = null;

        try {
            String date = cursor.getString(ArticleLoader.Query.PUBLISHED_DATE);
            publishedDate = dateFormat.parse(date);
        } catch (ParseException ex) {
            Log.e(TAG, ex.getMessage());
            Log.i(TAG, "passing today's date");
            publishedDate = new Date();
        }

        if (!publishedDate.before(START_OF_EPOCH.getTime())) {
            return android.text.format.DateUtils.getRelativeTimeSpanString(
                    publishedDate.getTime(),
                    System.currentTimeMillis(), android.text.format.DateUtils.HOUR_IN_MILLIS,
                    android.text.format.DateUtils.FORMAT_ABBREV_ALL).toString();
        }
        else
            return outputFormat.format(publishedDate);
    }

}
