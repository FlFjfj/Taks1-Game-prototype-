uniform mat4 u_Eye;
uniform vec4 u_Trans;

attribute vec4 a_Position;
attribute vec2 a_TexCoord;

varying vec2 v_TexCoord;



void main() {
   v_TexCoord = a_TexCoord;

   vec3 forw = normalize( vec3(-u_Trans.x, 0, -u_Trans.z));
   vec3 cur = vec3(0, 0, -1);
   float angle = float(1);
   float cosa = forw.x*cur.x+ forw.y*cur.y + forw.z*cur.z;
   vec3 vp = vec3(forw.y*cur.z-forw.z*cur.y,
                  forw.x*cur.z-forw.z*cur.x,
                  forw.x*cur.y-forw.y*cur.x);
   float sina = length(vp);
   mat4 rot = mat4(cosa,  0,sina, 0,
                      0,  1,   0, 0,
                  -sina,  0,cosa, 0,
                      0,  0,   0, 1);

   gl_Position = u_Eye * (rot * a_Position + u_Trans);
}