package monster;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.fjfj.prototype.MainGame;

public class Monster {

	public static Model model;
	public static BoundingBox box;
	ModelInstance instance;
	
	Vector3 pos;
	Vector3 speed;
	Vector3 scale = new Vector3(0.1f, 0.1f, 0.1f);
	Quaternion rotation = new Quaternion();
	
	public Monster(Vector3 pos){
		this.pos = pos;
		speed = pos.cpy().nor().scl(-5);
		pos.y = -box.getHeight() / 2 * scale.y;
		instance = new ModelInstance(model);
		
		instance.transform.set(pos, rotation, scale);
	
	}
	
	public void render(ModelBatch batch, Environment env){
		batch.render(instance, env);
	}
	
	public boolean update(){
		Vector3 delta = speed.cpy().scl(Gdx.graphics.getDeltaTime());
		pos.add(delta);
		if(!pos.hasOppositeDirection(speed))
			return false;
		
		instance.transform.set(pos, rotation, scale);
		return true;
	}
	
	public boolean isChoosed(Vector2 watch){
			Vector2 pos = new Vector2(this.pos.x, this.pos.z);
			if(watch.dot(pos.nor()) < -0.8f)
				return true;
			return false;
	}

	public Vector2 getFlatPosition(Vector2 watch) {
		Vector2 ans = new Vector2(-1000, 1);
		Vector2 pos = new Vector2(this.pos.x, this.pos.z);
		
		double angle = Math.toDegrees(Math.acos(watch.dot(pos.nor())));
		
		if(Math.abs(angle) <= 34 ){
			ans.x = (float) (- (MainGame.WIDTH + 100) / 2 / 34.0 * angle * pos.crs(watch)/Math.abs(pos.crs(watch)) - 100);
			ans.y = 1 - this.pos.len()/40f; 
		}
		
		return ans;
	}
	
}
