package com.fjfj.testvr;

import android.content.res.AssetManager;
import android.util.Log;
import android.os.SystemClock;

import com.fjfj.testvr.utility.FloatThread;
import com.fjfj.testvr.utility.WaveDecoder;
import com.google.vr.sdk.audio.GvrAudioEngine;
import com.google.vr.sdk.base.GvrActivity;

import java.io.InputStream;
import java.util.ArrayList;

public class AudioSupport {

    public static float[] resultsSound = null;
    public static long startTime;
    public static int SampleRate = 44100;

    private static final String OBJECT_SOUND_FILE = "test4.wav";
     GvrAudioEngine gvrAudioEngine;
    private volatile int sourceId = GvrAudioEngine.INVALID_ID;
    public FloatThread ftThread;
    public AudioSupport(final GvrActivity gvr) {

        gvrAudioEngine = new GvrAudioEngine(gvr, GvrAudioEngine.RenderingMode.BINAURAL_HIGH_QUALITY);
        AssetManager assetManager = gvr.getAssets();
        try {
            InputStream input = assetManager.open("test4.wav");
            WaveDecoder decoder = new WaveDecoder(input);
            float[] samples = new float[1024];
            int readSamples = 0;
            ArrayList<Float> samplesList = new ArrayList<Float>();
            while ((readSamples = decoder.readSamples(samples)) > 0) {
                for (int i = 0; i < readSamples; i++) {
                    samplesList.add(samples[i]);
                }
            }
            float[] result = new float[samplesList.size()];
            float max = 0;
            for (int i = 0; i < samplesList.size(); i++) {
                result[i] = Math.abs(samplesList.get(i));
                if (max < result[i]) {
                    max = result[i];
                }
            }
            result[0] = max;
            ftThread = new FloatThread(result);

        } catch (Exception e) {
            Log.e("Error", "Error", e);}
        ftThread.run();
        gvrAudioEngine.preloadSoundFile(OBJECT_SOUND_FILE);
        sourceId = gvrAudioEngine.createSoundObject(OBJECT_SOUND_FILE);
        gvrAudioEngine.playSound(sourceId, true /* looped playback */);
        startTime = SystemClock.elapsedRealtime();
    }

}
