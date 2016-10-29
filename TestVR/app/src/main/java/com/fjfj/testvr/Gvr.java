package com.fjfj.testvr;

import android.graphics.Shader;
import android.opengl.GLES20;
import android.opengl.Matrix;
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

    private static final float Z_NEAR = 0.1f;
    private static final float Z_FAR = 100.0f;

    Renderable triangle;
    float camera[] = new float[16];
    float view[] = new float[16];
    float model_view[] = new float[16];

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
        Matrix.setLookAtM(camera, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0);
        Matrix.setIdentityM(model_view, 0);
    }

    @Override
    public void onDrawEye(Eye eye) {
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glClearColor(1, 0, 0, 1);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        Matrix.multiplyMM(view, 0, eye.getEyeView(), 0, camera, 0);

        float perspective[] = eye.getPerspective(Z_NEAR, Z_FAR);
        Matrix.multiplyMM(model_view, 0, perspective, 0, view, 0);

        GLES20.glUniformMatrix4fv(Renderable.shader.eyeUniform, 1, false, model_view, 0);

        triangle.render();

    }

    @Override
    public void onFinishFrame(Viewport viewport) {

    }

    @Override
    public void onSurfaceChanged(int i, int i1) {

    }

    @Override
    public void onSurfaceCreated(EGLConfig eglConfig) {
        triangle = new Renderable(new float[]{0, 0.5f, 1,
                                             -0.5f, -0.5f, 1f,
                                              0.5f, 0.5f, 1},
                                  new float[]{1, 0, 0, 1,
                                              0, 1, 0, 1,
                                              0, 0, 1, 1}
        );

        Renderable.shader = new ShaderProgram(ShaderProgram.getFile(this, R.raw.vert),
                ShaderProgram.getFile(this, R.raw.frag));
    }

    @Override
    public void onRendererShutdown() {

    }
}
