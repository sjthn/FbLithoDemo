package com.example.srijith.fblithodemo;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.soloader.SoLoader;

/**
 * Created by Srijith on 28-04-2017.
 */

public class LithoDemoApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Fresco.initialize(this);
        SoLoader.init(this, false);
    }
}
