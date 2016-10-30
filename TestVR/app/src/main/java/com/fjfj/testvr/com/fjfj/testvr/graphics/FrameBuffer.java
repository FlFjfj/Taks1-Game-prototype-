package com.fjfj.testvr.com.fjfj.testvr.graphics;

import android.opengl.GLES20;

public class FrameBuffer {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    private int[] fbo = new int[1];
    private int[] color = new int[1];

    public FrameBuffer(){
        GLES20.glGenFramebuffers(1, fbo, 0);
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fbo[0]);

        GLES20.glGenTextures(1, color, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, color[0]);
        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0,GLES20.GL_RGB, HEIGHT, WIDTH, 0,
                                    GLES20.GL_RGB, GLES20.GL_UNSIGNED_BYTE, null);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);

        GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0,
                GLES20.GL_TEXTURE_2D, fbo[0], 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
    }

    public void begin(){
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fbo[0]);
    }

    public int getTex(){
        return color[0];
    }

    public void end(){
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
    }

}
