package com.fjfj.testvr.utility;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class WaveDecoder
{
    private final float MAX_VALUE = 1.0f / Short.MAX_VALUE;
    private EndianDataInputStream in;
    private int channels;
    private float sampleRate;

    public WaveDecoder( InputStream stream )
    {
        try {

            if (stream == null)
                throw new IllegalArgumentException("Input stream must not be null");

            in = new EndianDataInputStream(new BufferedInputStream(stream, 1024 * 1024));
            if (!in.read4ByteString().equals("RIFF"))
                throw new IllegalArgumentException("not a wav");

            in.readIntLittleEndian();

            if (!in.read4ByteString().equals("WAVE"))
                throw new IllegalArgumentException("expected WAVE tag");

            if (!in.read4ByteString().equals("fmt "))
                throw new IllegalArgumentException("expected fmt tag");

            if (in.readIntLittleEndian() != 16)
                throw new IllegalArgumentException("expected wave chunk size to be 16");

            if (in.readShortLittleEndian() != 1)
                throw new IllegalArgumentException("expected format to be 1");

            channels = in.readShortLittleEndian();
            sampleRate = in.readIntLittleEndian();
            if (sampleRate != 44100)
                throw new IllegalArgumentException("Not 44100 sampling rate");
            in.readIntLittleEndian();
            in.readShortLittleEndian();
            int fmt = in.readShortLittleEndian();

            if (fmt != 16)
                throw new IllegalArgumentException("Only 16-bit signed format supported");

            if (!in.read4ByteString().equals("data"))
                throw new RuntimeException("expected data tag");

            in.readIntLittleEndian();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int readSamples( float[] samples )
    {
        int readSamples = 0;
        for( int i = 0; i < samples.length; i++ )
        {
            float sample = 0;
            try
            {
                for( int j = 0; j < channels; j++ )
                {
                    int shortValue = in.readShortLittleEndian( );
                    sample += (shortValue * MAX_VALUE);
                }
                sample /= channels;
                samples[i] = sample;
                readSamples++;
            }
            catch( Exception ex )
            {
                break;
            }
        }

        return readSamples;
    }
}
