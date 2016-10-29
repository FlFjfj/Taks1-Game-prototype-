package com.fjfj.testvr.com.fjfj.testvr.graphics;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class PrimitiveRenderable
{

    private FloatBuffer verts;
    private int countVert;

    private FloatBuffer colors;

    private float[] trans = {0, 0, 0, 0};

    public static ShaderProgram shader;

    public PrimitiveRenderable(float[] verts, float[] colors){

        ByteBuffer bb = ByteBuffer.allocateDirect(verts.length * 4);
        bb.order(ByteOrder.nativeOrder());
        this.verts = bb.asFloatBuffer();
        this.verts.put(verts);
        this.verts.position(0);
        this.countVert = verts.length;

        bb = ByteBuffer.allocateDirect(colors.length * 4);
        bb.order(ByteOrder.nativeOrder());
        this.colors = bb.asFloatBuffer();
        this.colors.put(colors);
        this.colors.position(0);
    }

    public void setTrans(float x, float y, float z){
        trans[0] = x;
        trans[1] = y;
        trans[2] = z;
    }

    public void render(){
        GLES20.glVertexAttribPointer(shader.positionAttrib, 3, GLES20.GL_FLOAT, false, 0, verts);
        GLES20.glVertexAttribPointer(shader.colorAttrib, 4, GLES20.GL_FLOAT, false, 0, colors);

        GLES20.glUniform4f(shader.transUniform, trans[0], trans[1], trans[2], trans[3]);

        GLES20.glEnableVertexAttribArray(shader.positionAttrib);
        GLES20.glEnableVertexAttribArray(shader.colorAttrib);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, countVert);
    }

}
