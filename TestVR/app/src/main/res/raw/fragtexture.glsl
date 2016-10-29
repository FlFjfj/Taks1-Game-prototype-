precision mediump float;

uniform mat4 u_Eye;

varying vec4 v_Color;

void main() {
    gl_FragColor = v_Color;
}