package com.fjfj.prototype;

import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.LifecycleListener;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.CardBoardAndroidApplication;
import com.badlogic.gdx.backends.android.CardBoardApplicationListener;
import com.badlogic.gdx.backends.android.CardboardCamera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.SnapshotArray;
import com.google.vrtoolkit.cardboard.Eye;
import com.google.vrtoolkit.cardboard.HeadTransform;
import com.google.vrtoolkit.cardboard.Viewport;

import android.os.Bundle;
import monster.Monster;
import monster.MonsterFactory;

public class AndroidLauncher extends CardBoardAndroidApplication implements CardBoardApplicationListener{
	
	CardboardCamera cam;
	ModelBatch batch;
	Environment env;

	Vector<Monster> monsters;
	MonsterFactory fact;
	
	ModelInstance world;
	Texture guiTex;
	Texture eyeTex;
	
	int score = 0;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.numSamples = 4;
		initialize(this, config);
	}

	@Override
	public void create() {
		/*Assets.init();
		
		batch = new ModelBatch();

		cam = new CardboardCamera();
		cam.near = GameConst.Z_NEAR;
		cam.far = GameConst.Z_FAR;
		cam.position.set(0, 0, 0);
		cam.lookAt(0, 0, 0);
		cam.update();

		env = new Environment();
		env.set(new ColorAttribute(ColorAttribute.AmbientLight, 1f, 1f, 1f, 1f));
		env.add(new DirectionalLight().set(1f, 1f, 1f, 1f, -1f, 1f));

		Monster.model = Assets.assets.get("models/monster/monster.obj", Model.class);
		Monster.box = Monster.model.calculateBoundingBox(new BoundingBox());
		monsters = new Vector<Monster>();

		fact = new MonsterFactory(monsters);

		world = new ModelInstance(Assets.assets.get("models/sphere/sphere.g3db", Model.class));
		world.transform.translate(0, -100, 0);*/
		
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void render() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public SnapshotArray<LifecycleListener> getLifecycleListeners() {
		return null;
	}

	@Override
	public void onNewFrame(HeadTransform paramHeadTransform) {
		/*fact.update();

		Vector<Monster> delete = new Vector<Monster>();
		Vector2 watch = new Vector2(cam.direction.x, cam.direction.z).nor();
		for (Monster m : monsters)
			if (!m.update()){
				delete.add(m);
				if(m.isChoosed(watch))
					score++;
				else
					score--;
			}
		monsters.removeAll(delete);

		float vect[] = new float[4];
		paramHeadTransform.getQuaternion(vect, 0);
		cam.lookAt(0, 0, -1);
		cam.rotate(new Quaternion(vect[0], vect[1], vect[2], vect[3]));
		
		cam.update();*/
	}

	@Override
	public void onDrawEye(Eye eye) {
	
		/*cam.setEyeViewAdjustMatrix(new Matrix4(eye.getEyeView()));

	    float[] perspective = eye.getPerspective(GameConst.Z_NEAR, GameConst.Z_FAR);
	    cam.setEyeProjection(new Matrix4(perspective));
	    cam.update();
		
	    Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT
				| (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));

		batch.begin(cam);

		batch.render(world, env);

		for (Monster m : monsters)
			m.render(batch, env);

		batch.end();*/
	    
	}

	@Override
	public void onFinishFrame(Viewport paramViewport) {
	}

	@Override
	public void onRendererShutdown() {
		// TODO Auto-generated method stub
		
	}
}
