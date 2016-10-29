package com.fjfj.testvr.GamePlay;

import com.fjfj.testvr.utility.Timing;

import java.util.Random;
import java.util.Vector;

public class MonsterFactory {

    Vector<Monster> monsters;
    Random rand = new Random();
    float delta = 1;

    public MonsterFactory(Vector<Monster> monsters){
        this.monsters = monsters;
    }

    public void update(){
        delta -= Timing.getDelta();
        if(delta <= 0){
            delta += rand.nextFloat() * 2;
            monsters.add(new Monster(new float[]{ rand.nextFloat() * 50 - 25, 0,
                                                  rand.nextFloat()* 50 - 25}));
        }
    }

}
