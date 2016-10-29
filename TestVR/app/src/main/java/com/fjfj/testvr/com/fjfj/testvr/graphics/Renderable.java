package com.fjfj.testvr.com.fjfj.testvr.graphics;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Renderable
{

    private FloatBuffer verts;
    private int count;

    public static ShaderProgram shader;

    public Renderable(float[] verts){

        ByteBuffer bb = ByteBuffer.allocateDirect(verts.length * 4);
        bb.order(ByteOrder.nativeOrder());
        this.verts = bb.asFloatBuffer();
        this.verts.put(verts);
        this.verts.position(0);
        this.count = verts.length;
    }

    public void render(){

        shader.begin();

        GLES20.glVertexAttribPointer(shader.positionAttrib, 3, GLES20.GL_FLOAT, false, 0, verts);

        GLES20.glEnableVertexAttribArray(shader.positionAttrib);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, count);

        shader.end();
    }

}
