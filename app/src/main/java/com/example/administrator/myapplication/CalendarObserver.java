package com.example.administrator.myapplication;


import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

public class CalendarObserver extends ContentObserver {
    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    public CalendarObserver(Handler handler) {
        super(handler);
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
    }
}
