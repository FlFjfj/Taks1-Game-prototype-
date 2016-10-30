package com.fjfj.testvr.utility;

/**
 * Created by arento on 30.10.16.
 */

public class FloatThread implements Runnable {
    private float[] results;
    private float[] samples;
    private boolean ready;
    public FloatThread(float[] samples){
        results = new float[samples.length];
        this.samples = samples;
        ready = false;
    }
    public void run() {
        results[0] = samples[0];
        for (int i = 1; i < results.length; i++) {
            results[i] = results[i-1]+samples[i];
        }
        ready = true;
        return;
    }
    public float[] getResults(){
        if(ready)
            return results;
        else
            return null;
    }
}
