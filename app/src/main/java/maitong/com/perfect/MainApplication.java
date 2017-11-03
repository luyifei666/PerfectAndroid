package maitong.com.perfect;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.util.LruCache;


public final class MainApplication extends Application {

    private static Context mContext;
    private static LocalBroadcastManager localBroadcastManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }

    public static LocalBroadcastManager getLocalBroadcastManager() {
        return localBroadcastManager;
    }
}
