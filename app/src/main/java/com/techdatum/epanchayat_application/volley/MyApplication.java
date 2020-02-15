package com.techdatum.epanchayat_application.volley;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


/**
 * Created by SSV-I3-1 on 25-01-2016.
 */
public class MyApplication extends Application {

    public static final String TAG = MyApplication.class
            .getSimpleName();
    public String MY_PREFS_NAME = "MyPrefsFile";
    private RequestQueue mRequestQueue;
    private static MyApplication sInstance;
    private MyPreferenceManager pref;

    public static MyApplication getInstance() {
        return sInstance;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }

    public MyPreferenceManager getPrefManager() {
        if (pref == null) {
            pref = new MyPreferenceManager(this);
        }

        return pref;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

    }


    public String getMY_PREFS_NAME() {
        return MY_PREFS_NAME;
    }

    public void setMY_PREFS_NAME(String someVariable) {
        this.MY_PREFS_NAME = someVariable;
    }
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }


    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}
