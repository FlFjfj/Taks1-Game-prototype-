package com.fjfj.testvr;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.Bundle;

import com.fjfj.testvr.GamePlay.Monster;
import com.fjfj.testvr.GamePlay.MonsterFactory;
import com.fjfj.testvr.com.fjfj.testvr.graphics.PrimitiveRenderable;
import com.fjfj.testvr.com.fjfj.testvr.graphics.ShaderProgram;
import com.fjfj.testvr.com.fjfj.testvr.graphics.TextureRenderer;
import com.fjfj.testvr.utility.Timing;
import com.fjfj.testvr.utility.Vector3;
import com.google.vr.sdk.base.Eye;
import com.google.vr.sdk.base.GvrActivity;
import com.google.vr.sdk.base.GvrView;
import com.google.vr.sdk.base.HeadTransform;
import com.google.vr.sdk.base.Viewport;

import java.util.Vector;

import javax.microedition.khronos.egl.EGLConfig;

public class Gvr extends GvrActivity implements GvrView.StereoRenderer{

    private static final float Z_NEAR = 0.1f;
    private static final float Z_FAR = 100.0f;

    float camera[] = new float[16];
    float view[] = new float[16];
    float modelView[] = new float[16];

    MonsterFactory factory;
    Vector<Monster> monsters;

    CameraRenderer camRend;

    //FrameBuffer fb;
    TextureRenderer screen;

    TextureRenderer monster;
    int image;

    int score;

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
        Matrix.setLookAtM(camera, 0, 0, 0, 0.01f, 0, 0, 0, 0, 1, 0);

        factory.update();
        camRend.update();

        float forward[] = new float[3];
        headTransform.getForwardVector(forward, 0);
        Vector<Monster> delete = new Vector<Monster>();
        Vector3 watch = new Vector3(forward).nor();
        for (Monster m : monsters)
            if (!m.update(watch)){
                delete.add(m);
                if(m.isChoosed(watch))
                    score++;
                else
                    score--;
            }
        monsters.removeAll(delete);
}

    @Override
    public void onDrawEye(Eye eye) {

        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glClearColor(1, 1, 1, 1);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        camRend.render();

        TextureRenderer.shader.begin();

        Matrix.multiplyMM(view, 0, eye.getEyeView(), 0, camera, 0);

        float[] perspective = eye.getPerspective(Z_NEAR, Z_FAR);
        Matrix.multiplyMM(modelView, 0, perspective, 0, eye.getEyeView(), 0);

        GLES20.glUniformMatrix4fv(PrimitiveRenderable.shader.eyeUniform, 1, false, modelView, 0);

        for(int i = monsters.size() - 1; i >= 0; i--)
           monsters.get(i).render();

        TextureRenderer.shader.end();

    }

    @Override
    public void onFinishFrame(Viewport viewport) {;
        Timing.update();
    }

    @Override
    public void onSurfaceChanged(int i, int i1) {

    }

    @Override
    public void onSurfaceCreated(EGLConfig eglConfig) {
        PrimitiveRenderable.shader = new ShaderProgram(ShaderProgram.getFile(this, R.raw.vert),
                ShaderProgram.getFile(this, R.raw.frag));

        monsters = new Vector<Monster>();
        factory = new MonsterFactory(monsters);
        camRend = new CameraRenderer(this);

        TextureRenderer.shader = new ShaderProgram(
                ShaderProgram.getFile(this, R.raw.verttexture),
                ShaderProgram.getFile(this, R.raw.fragtexture));

        Monster.images = TextureRenderer.loadTextures(this.getApplicationContext(),
                R.raw.monster1, R.raw.monster2, R.raw.monster3);
        monster = new TextureRenderer(image, 3, 3);
        Monster.rend = monster;
    }

    @Override
    public void onRendererShutdown() {

    }
}
