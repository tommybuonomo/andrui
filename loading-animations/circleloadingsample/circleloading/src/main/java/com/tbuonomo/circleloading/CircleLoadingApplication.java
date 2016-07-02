package com.tbuonomo.circleloading;

import android.app.Application;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by tommy on 02/07/16.
 */
public class CircleLoadingApplication extends Application {
    public static Point dimensions = new Point();

    @Override
    public void onCreate() {
        super.onCreate();
        setUpScreenDimensions();
    }

    private void setUpScreenDimensions() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        display.getSize(dimensions);
    }
}
