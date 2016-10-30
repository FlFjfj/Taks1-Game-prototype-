precision mediump float;

uniform mat4 u_Eye;
uniform vec4 u_Trans;

uniform sampler2D texFramebuffer;

varying vec2 v_TexCoord;

void main() {
    gl_FragColor = texture2D(texFramebuffer, v_TexCoord);
}