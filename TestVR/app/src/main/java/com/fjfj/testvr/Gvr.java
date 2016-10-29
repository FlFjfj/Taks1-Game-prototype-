package com.fjfj.testvr;

import android.graphics.Shader;
import android.opengl.GLES20;
import android.os.Bundle;

import com.fjfj.testvr.com.fjfj.testvr.graphics.Renderable;
import com.fjfj.testvr.com.fjfj.testvr.graphics.ShaderProgram;
import com.google.vr.sdk.base.Eye;
import com.google.vr.sdk.base.GvrActivity;
import com.google.vr.sdk.base.GvrView;
import com.google.vr.sdk.base.HeadTransform;
import com.google.vr.sdk.base.Viewport;

import javax.microedition.khronos.egl.EGLConfig;

public class Gvr extends GvrActivity implements GvrView.StereoRenderer{

    Renderable triangle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gvr);

        GvrView gvrView = (GvrView) findViewById(R.id.gvr_view);
        gvrView.setRenderer(this);
        setGvrView(gvrView);

    }

    @Override
    public void onNewFrame(HeadTransform headTransform) {

    }

    @Override
    public void onDrawEye(Eye eye) {
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glClearColor(1, 0, 0, 1);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        //triangle.render();

    }

    @Override
    public void onFinishFrame(Viewport viewport) {

    }

    @Override
    public void onSurfaceChanged(int i, int i1) {

    }

    @Override
    public void onSurfaceCreated(EGLConfig eglConfig) {
        triangle = new Renderable(new float[]{-1, 0, 0,
                1, 0, 0,
                0, 1, 0}
        );

        ShaderProgram shader = new ShaderProgram(ShaderProgram.getFile(this, R.raw.vert),
                ShaderProgram.getFile(this, R.raw.frag));
        Renderable.shader = shader;
    }

    @Override
    public void onRendererShutdown() {

    }
}
