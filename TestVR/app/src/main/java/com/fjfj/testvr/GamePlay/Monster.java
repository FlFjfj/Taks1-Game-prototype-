package com.fjfj.testvr.GamePlay;

import com.fjfj.testvr.com.fjfj.testvr.graphics.TextureRenderer;
import com.fjfj.testvr.utility.Timing;
import com.fjfj.testvr.utility.Vector3;

import java.util.Random;

public class Monster {

    public static TextureRenderer rend;
    public static int[] images;
    public int myImage;

    Vector3 pos;
    Vector3 speed;

    //boolean isWatched = false;

    public Monster(float pos[]){
        this.pos = new Vector3(pos);
        speed = this.pos.cpy().nor().scl(-5);

        myImage = (new Random()).nextInt(3);
    }

    public void render(){

        rend.texture = images[myImage];

        rend.setTrans(pos.x, pos.y, pos.z);
        rend.render();

    }

    public boolean update(Vector3 watch){

        Vector3 delta = speed.cpy().scl(Timing.getDelta());
        pos.add(delta);
        if(!pos.hasOppositeDirection(speed))
            return false;
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
