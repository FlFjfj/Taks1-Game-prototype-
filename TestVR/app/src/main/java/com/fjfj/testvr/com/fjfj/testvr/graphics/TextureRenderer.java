package com.fjfj.testvr.com.fjfj.testvr.graphics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class TextureRenderer {

    public static ShaderProgram shader;

    private FloatBuffer verts;
    private int countVert;

    private int texture;

    public TextureRenderer(int texture, float width, float height){
        ByteBuffer bb = ByteBuffer.allocateDirect(6 * 4);
        bb.order(ByteOrder.nativeOrder());
        this.verts = bb.asFloatBuffer();
        this.verts.put(new float[]{
                -width/2, -height/2, 0,
                -width/2,  height/2, 0,
                 width/2,  height/2, 0,
                -width/2, -height/2, 0,
                 width/2, -height/2, 0,
                 width/2,  height/2, 0,
        });
        this.verts.position(0);
        this.countVert = 6;

        this.texture = texture;

    }

}
