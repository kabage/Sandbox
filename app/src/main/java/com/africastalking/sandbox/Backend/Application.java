package com.africastalking.sandbox.Backend;

import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by kabage on 11/4/15.
 */
public class                                                                                                                                                                                                                                                                                                                                                                                                                        Application extends android.app.Application {

    @Override
    public void onCreate() {

        super.onCreate();

    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
