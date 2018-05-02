package com.cyrillrx.android.demo;

import android.app.Application;

import com.cyrillrx.logger.Logger;

/**
 * @author Cyril Leroux
 *         Created on 02/05/2018
 */
public class DemoApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Logger.initialize();
    }
}
