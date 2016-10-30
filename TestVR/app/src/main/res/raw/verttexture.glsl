uniform mat4 u_Eye;
uniform vec4 u_Trans;

attribute vec4 a_Position;
attribute vec2 a_TexCoord;

varying vec2 v_TexCoord;

void main() {
   v_TexCoord = a_TexCoord;
   gl_Position = u_Eye * (a_Position + u_Trans);
}