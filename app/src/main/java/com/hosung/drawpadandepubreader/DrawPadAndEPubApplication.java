package com.hosung.drawpadandepubreader;

import android.app.Application;
import android.util.Log;

import io.realm.Realm;

/**
 * Created by Hosung, Lee on 2017. 5. 23..
 */



public class DrawPadAndEPubApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
