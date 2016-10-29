package com.fjfj.testvr.com.fjfj.testvr.graphics;

import android.opengl.GLES20;

import com.google.vr.sdk.base.GvrActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ShaderProgram {

    private int shader;
    public int positionAttrib;

    public ShaderProgram(String vertId, String fragId){

        int vertShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        GLES20.glShaderSource(vertShader, vertId);
        GLES20.glCompileShader(vertShader);
        int status[] = new int[1];
        GLES20.glGetShaderiv(vertShader, GLES20.GL_COMPILE_STATUS, status, 0);
        if(status[0] == 0){
            System.out.println("Error compiling shader: " + GLES20.glGetShaderInfoLog(vertShader));
            System.exit(-1);
        }

        int fragShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        GLES20.glShaderSource(fragShader, fragId);
        GLES20.glCompileShader(vertShader);
        GLES20.glGetShaderiv(fragShader, GLES20.GL_COMPILE_STATUS, status, 0);
        if(status[0] == 0){
            System.out.println("Error compiling shader: " + GLES20.glGetShaderInfoLog(fragShader));
            System.exit(-1);
        }

        shader = GLES20.glCreateProgram();
        GLES20.glAttachShader(shader, vertShader);
        GLES20.glAttachShader(shader, fragShader);
        GLES20.glLinkProgram(shader);
        GLES20.glUseProgram(shader);

        GLES20.glGetAttribLocation(shader, "a_Position");
    }

    public void begin(){
        GLES20.glUseProgram(shader);

        GLES20.
    }

    public void end(){
        GLES20.glUseProgram(0);
    }

    public static String getFile(GvrActivity act, int resId){
        InputStream inputStream = act.getResources().openRawResource(resId);
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            reader.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
