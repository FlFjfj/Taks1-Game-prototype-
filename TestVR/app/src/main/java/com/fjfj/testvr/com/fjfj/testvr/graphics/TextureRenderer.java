package com.fjfj.testvr.com.fjfj.testvr.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class TextureRenderer {

    public static ShaderProgram shader;

    private FloatBuffer verts;
    private FloatBuffer tex_coords;
    private int countVert;

    public int texture;

    private float[] trans = {0, 0, 0, 0};

    public TextureRenderer(int texture, float width, float height){
        ByteBuffer bb = ByteBuffer.allocateDirect(6 * 3 * 4);
        bb.order(ByteOrder.nativeOrder());
        this.verts = bb.asFloatBuffer();
        this.verts.put(new float[]{
                -width/2, -height/2, 0,
                -width/2,  height/2, 0,
                 width/2,  height/2, 0,

                -width/2, -height/2, 0,
                 width/2, -height/2, 0,
                 width/2,  height/2, 0
        });
        this.verts.position(0);
        this.countVert = 6;

        bb = ByteBuffer.allocateDirect(6 * 2 * 4);
        bb.order(ByteOrder.nativeOrder());
        this.tex_coords = bb.asFloatBuffer();
        this.tex_coords.put(new float[]{
                0, 1,
                0, 0,
                1, 0,

                0, 1,
                1, 1,
                1, 0
        });
        this.tex_coords.position(0);

        this.texture = texture;

    }

    public void render(){
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture);

        GLES20.glEnableVertexAttribArray(shader.positionAttrib);
        GLES20.glVertexAttribPointer(shader.positionAttrib, 3, GLES20.GL_FLOAT,
                false, 0, verts);

        int mTextureCoordHandle = GLES20.glGetAttribLocation(shader.shader, "a_TexCoord");
        GLES20.glEnableVertexAttribArray(mTextureCoordHandle);
        GLES20.glVertexAttribPointer(mTextureCoordHandle, 2, GLES20.GL_FLOAT,
                false, 0, tex_coords);

        GLES20.glUniform4f(TextureRenderer.shader.transUniform, trans[0], trans[1], trans[2], 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);
    }

    public void setTrans(float x, float y, float z){
        trans[0] = x;
        trans[1] = y;
        trans[2] = z;
    }

    public static int[] loadTextures(final Context context, int... resIDs)
    {
        final int[] textureHandle = new int[resIDs.length];

        GLES20.glGenTextures(resIDs.length, textureHandle, 0);

        for(int i=0; i<textureHandle.length; i++){
            if (textureHandle[i] != 0)
            {
                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inScaled = false;   // No pre-scaling

                final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resIDs[i], options);
                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[i]);

                GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
                GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
                GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

                bitmap.recycle();
            }
            if (textureHandle[i] == 0)
            {
                throw new RuntimeException("Error loading texture.");
            }
        }


        return textureHandle;
    }

}
