package com.fjfj.testvr;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.opengl.GLES11;
import android.opengl.GLES20;
import android.os.SystemClock;

import com.fjfj.testvr.com.fjfj.testvr.graphics.ShaderProgram;
import com.fjfj.testvr.utility.Timing;
import com.google.vr.sdk.base.GvrActivity;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class CameraRenderer implements SurfaceTexture.OnFrameAvailableListener {

    ShaderProgram shader;

    private static final int GL_TEXTURE_EXTERNAL_OES = 0x8D65;

    private FloatBuffer vertexBuffer, textureVerticesBuffer;
    static final int COORDS_PER_VERTEX = 3;


    static float squareVertices[] = { // in counterclockwise order:
             0, 0, 1,
             1, -1,1,
            -1,-1, 1,

            0,0,1,
            1,1,1,
            1,-1,1,

            0,0,1,
            -1,1,1,
            1,1,1,

            0,0,1,
            -1,-1,1,
            -1,1,1

    };

    static float textureVertices[] = {
            0.5f,0.5f,
            1,   1,
            0,   1,

            0.5f,0.5f,
            1,0,
            1,1,

            0.5f,0.5f,
            0,0,
            1,0,

            0.5f,0.5f,
            0,1,
            0,0
    };

    private Camera camera;
    public int texture;
    private SurfaceTexture surface;
    private int mPositionHandle;
    private int mTextureCoordHandle;

    float delta = 0;

    public CameraRenderer(GvrActivity gvr) {
        ByteBuffer bb = ByteBuffer.allocateDirect(squareVertices.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(squareVertices);
        vertexBuffer.position(0);

        ByteBuffer bb2 = ByteBuffer.allocateDirect(textureVertices.length * 4);
        bb2.order(ByteOrder.nativeOrder());
        textureVerticesBuffer = bb2.asFloatBuffer();
        textureVerticesBuffer.put(textureVertices);
        textureVerticesBuffer.position(0);

        shader = new ShaderProgram(ShaderProgram.getFile(gvr, R.raw.vertcam),
                ShaderProgram.getFile(gvr, R.raw.fragcam));

        texture = createTexture();
        startCamera(texture);
    }

    private void genVerts() {

        float sampleInfluense = 1;

        if(AudioSupport.resultsSound != null) {
            float timeFromStart = SystemClock.elapsedRealtime() - AudioSupport.startTime;
            int samplesFromStart = (int) (timeFromStart / 1000f * AudioSupport.SampleRate);
            int realSamplesBack = Math.max(0, (samplesFromStart) % AudioSupport.resultsSound.length - 1000);
            if(realSamplesBack!=0)
                 sampleInfluense = AudioSupport.resultsSound[samplesFromStart%AudioSupport.resultsSound.length]/realSamplesBack;
        }
            float y = (float) (Math.sin(delta * sampleInfluense) / 4f);
            float x = (float) (Math.sin(delta + sampleInfluense) / 4f);

            squareVertices[1] = y;
            squareVertices[10] = y;
            squareVertices[19] = y;
            squareVertices[28] = y;

            squareVertices[0] = x;
            squareVertices[9] = x;
            squareVertices[18] = x;
            squareVertices[27] = x;

            vertexBuffer.put(squareVertices);
            vertexBuffer.position(0);

    }

    public void startCamera(int texture) {
        surface = new SurfaceTexture(texture);
        surface.setOnFrameAvailableListener(this);
        camera = Camera.open();

        try
        {
            camera.setPreviewTexture(surface);
            camera.startPreview();
        }
        catch (IOException e) {e.printStackTrace();}
    }

    static private int createTexture()
    {
        int[] texture = new int[1];

        GLES20.glGenTextures(1,texture, 0);
        GLES20.glBindTexture(GL_TEXTURE_EXTERNAL_OES, texture[0]);
        GLES20.glTexParameterf(GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_LINEAR);
        GLES20.glTexParameterf(GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        GLES20.glTexParameteri(GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);

        return texture[0];
    }

    public void update(){
        delta += Timing.getDelta();
        genVerts();
        surface.updateTexImage();
    }

    public void render(){
        shader.begin();

        GLES20.glActiveTexture(GL_TEXTURE_EXTERNAL_OES);
        GLES20.glBindTexture(GL_TEXTURE_EXTERNAL_OES, texture);

        mPositionHandle = shader.positionAttrib;
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT,
                false,0, vertexBuffer);

        mTextureCoordHandle = GLES20.glGetAttribLocation(shader.shader, "inputTextureCoordinate");
        GLES20.glEnableVertexAttribArray(mTextureCoordHandle);
        GLES20.glVertexAttribPointer(mTextureCoordHandle, 2, GLES20.GL_FLOAT,
                false, 0, textureVerticesBuffer);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 12);

        GLES20.glUseProgram(0);
        GLES20.glActiveTexture(0);
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {

    }
}
