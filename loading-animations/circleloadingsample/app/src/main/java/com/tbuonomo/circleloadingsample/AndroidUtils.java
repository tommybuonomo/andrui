package com.tbuonomo.circleloadingsample;

import android.animation.Animator;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class AndroidUtils {
    public static void hideStatusBar(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static void setVisibility(int visibility, View... views) {
        for (View v : views) {
            v.setVisibility(visibility);
        }
    }

    public static void cancelAnimators(Animator... animators) {
        for (Animator a : animators) {
            if (a != null) {
                a.cancel();
            }
        }
    }

    public static void startAnimators(Animator... animators) {
        for (Animator a : animators) {
            if (a != null) {
                a.start();
            }
        }
    }
}
