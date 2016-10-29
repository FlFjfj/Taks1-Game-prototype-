package com.fjfj.testvr.utility;

import android.os.SystemClock;

public class Timing {

    private static long lastFrame = SystemClock.elapsedRealtime();

    public static float getDelta(){
        return (SystemClock.elapsedRealtime() - lastFrame)/ 1000f;
    }

    public static void update(){
        lastFrame = SystemClock.elapsedRealtime();
    }

}
