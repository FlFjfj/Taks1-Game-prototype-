#extension GL_OES_EGL_image_external : require
precision mediump float; 

varying vec2 textureCoordinate;                             

uniform samplerExternalOES s_texture;
uniform mat4 u_TransColor;

void main(void) {
    vec4 color = u_TransColor * texture2D( s_texture, textureCoordinate );
    color = color - vec4(int(color.r),int(color.g),int(color.b), int(color.a));
    gl_FragColor = vec4(color.rgb, texture2D( s_texture, textureCoordinate ).a);
}