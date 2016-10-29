package com.fjfj.testvr.GamePlay;

import com.fjfj.testvr.com.fjfj.testvr.graphics.PrimitiveRenderable;
import com.fjfj.testvr.utility.Timing;
import com.fjfj.testvr.utility.Vector3;

public class Monster {

    static PrimitiveRenderable rend;
    //static PrimitiveRenderable chosed;

    Vector3 pos;
    Vector3 speed;

    boolean isWatched = false;
    float angle = 0;

    {
        rend = new PrimitiveRenderable(new float[]{0, 1f, 0f,
                -1f, -1f, 0f,
                1f, -1f, 0f},
                new float[]{1, 0, 0, 1,
                            1, 0, 0, 1,
                            1, 0, 0, 1}
        );

        /*chosed = new PrimitiveRenderable(new float[]{0, 1f, 0f,
                -1f, -1f, 0f,
                 1f, -1f, 0f},
                new float[]{0, 0, 1, 1,
                            0, 0, 1, 1,
                            0, 0, 1, 1}
        );*/

    }

    public Monster(float pos[]){
        this.pos = new Vector3(pos);
        speed = this.pos.cpy().nor().scl(-1);

    }

    public void render(){
        //if(isWatched)
        //    chosed.render();
        //else
            rend.render();

    }

    public boolean update(Vector3 watch){

        //isWatched = isChoosed(watch);

        angle += Timing.getDelta();

        Vector3 delta = speed.cpy().scl(Timing.getDelta());
        pos.add(delta);
        if(!pos.hasOppositeDirection(speed))
            return false;

        //if(isWatched)
        //    chosed.setTrans(pos.x, pos.y, pos.z);
        //else
            rend.setTrans(pos.x, pos.y, pos.z);
        return true;
    }

    public boolean isChoosed(Vector3 watch){
        Vector3 pos = new Vector3(this.pos.x, 0,this.pos.z);
        if(watch.dot(pos.nor()) < -0.8f)
            return true;
        return false;
    }

    /*public Vector3 getFlatPosition(Vector3 watch) {
        Vector3 ans = new Vector3(-1000, 1);
        Vector3 pos = new Vector3(this.pos.x, this.pos.z);

        double angle = Math.toDegrees(Math.acos(watch.dot(pos.nor())));

        if(Math.abs(angle) <= 34 ){
            ans.x = (float) (- (MainGame.WIDTH + 100) / 2 / 34.0 * angle * pos.crs(watch)/Math.abs(pos.crs(watch)) - 100);
            ans.y = 1 - this.pos.len()/40f;
        }

        return ans;
    }*/

}
