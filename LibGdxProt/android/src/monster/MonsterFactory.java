package monster;

import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class MonsterFactory {

	Vector<Monster> monsters;
	
	float delta = 1;
	
	public MonsterFactory(Vector<Monster> monsters){
		this.monsters = monsters;
	}
	
	public void update(){
		delta -= Gdx.graphics.getDeltaTime();
		if(delta <= 0){
			delta += MathUtils.random(2);
			monsters.add(new Monster(new Vector3(MathUtils.random()* 50 - 25, 0, MathUtils.random()* 50 - 25)));
		}
	}
	
}
