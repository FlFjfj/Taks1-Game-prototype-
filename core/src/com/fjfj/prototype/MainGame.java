package com.fjfj.prototype;

import java.util.Vector;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.BoundingBox;

public class MainGame extends ApplicationAdapter {

	public static final int WIDTH = 1280/2;
	public static final int HEIGHT = 720;
	
	
	OrthographicCamera renderCam;
	FrameBuffer fbo;
	Texture proj;
	Texture mask;
	
	ModelBatch batch;
	Environment env;
	PerspectiveCamera cam;
	
	OrthographicCamera camGui;
	SpriteBatch batchGui;
	BitmapFont font;
	
	AssetManager assets;

	Vector<Monster> monsters;
	MonsterFactory fact;

	ModelInstance world;
	Texture guiTex;
	Texture eyeTex;
	
	int score = 0;
	
	@Override
	public void create() {
		batch = new ModelBatch();

		Gdx.input.setCursorCatched(true);
		
		cam = new PerspectiveCamera(67, WIDTH, HEIGHT);
		cam.near = 0.001f;
		cam.far = 16000;
		cam.position.set(0, 0, 0);
		cam.lookAt(1, 0, 0);
		cam.update();

		env = new Environment();
		env.set(new ColorAttribute(ColorAttribute.AmbientLight, 1f, 1f, 1f, 1f));
		env.add(new DirectionalLight().set(1f, 1f, 1f, 1f, -1f, 1f));

		assets = new AssetManager();
		assets.load("models/monster/monster.obj", Model.class);
		assets.load("models/sphere/sphere.g3db", Model.class);
		assets.load("textures/gui.png", Texture.class);
		assets.load("textures/eye.png", Texture.class);
		assets.load("textures/mask.png", Texture.class);
		while (!assets.update())
			;

		Monster.model = assets.get("models/monster/monster.obj", Model.class);
		Monster.box = Monster.model.calculateBoundingBox(new BoundingBox());
		monsters = new Vector<Monster>();

		fact = new MonsterFactory(monsters);

		world = new ModelInstance(assets.get("models/sphere/sphere.g3db", Model.class));
		world.transform.translate(0, -100, 0);
	
		camGui = new OrthographicCamera(WIDTH, HEIGHT);
		batchGui = new SpriteBatch();
		font = new BitmapFont();
		guiTex = assets.get("textures/gui.png", Texture.class);
		eyeTex = assets.get("textures/eye.png", Texture.class);
		
		renderCam = new OrthographicCamera(WIDTH * 2, HEIGHT);
		fbo = new FrameBuffer(Format.RGBA8888, WIDTH, HEIGHT, true);
		proj = fbo.getColorBufferTexture();
		mask = assets.get("textures/mask.png", Texture.class);
	}

	@Override
	public void render() {

		update();

		fbo.begin();
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT
				| (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));

		batch.begin(cam);

		batch.render(world, env);

		for (Monster m : monsters)
			m.render(batch, env);

		batch.end();

		drawGui();
		
		fbo.end();
		
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batchGui.setProjectionMatrix(renderCam.combined);
		
		batchGui.begin();
		batchGui.draw(proj, -WIDTH, -HEIGHT/2, WIDTH, HEIGHT, 0, 0, WIDTH, HEIGHT, false, true);
		batchGui.draw(proj, 0, -HEIGHT/2, WIDTH, HEIGHT, 0, 0, WIDTH, HEIGHT, false, true);
		batchGui.draw(mask, -WIDTH, -HEIGHT/2, WIDTH * 2, HEIGHT);
		font.draw(batchGui, "GYRO x " + (int)Gdx.input.getGyroscopeX()*100 , -WIDTH, 0);
		font.draw(batchGui, "GYRO y " + (int)Gdx.input.getGyroscopeY()*100 , -WIDTH, 20);
		font.draw(batchGui, "GYRO z " + (int)Gdx.input.getGyroscopeZ()*100 , -WIDTH, 40);
		
		batchGui.end();
		
		
	}

	private void update() {

		fact.update();

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

		//cam.rotate(Vector3.Y, -2 * Gdx.input.getDeltaX());
		cam.lookAt(MathUtils.round(MathUtils.sin(Gdx.input.getAccelerometerY())), 0, MathUtils.round(MathUtils.cos(Gdx.input.getAccelerometerY())));
		
		cam.update();
	}

	private void drawGui() {
		batchGui.setProjectionMatrix(camGui.combined);
		
		batchGui.begin();
		
		batchGui.draw(guiTex, -WIDTH/2, -HEIGHT/2, WIDTH, HEIGHT);
		
		Vector2 watch = new Vector2(cam.direction.x, cam.direction.z).nor().scl(-1);
		for (Monster m : monsters){
			Vector2 pos = m.getFlatPosition(watch);
			if(pos.x < WIDTH/2 && pos.x >= -WIDTH/2-100){
				batchGui.draw(eyeTex, pos.x, HEIGHT / 2 - 55, pos.y * 100, pos.y * 50);
			}
		}
		
		font.draw(batchGui, "FPS = " + Gdx.graphics.getFramesPerSecond(), -WIDTH/2+5, HEIGHT / 2-5);
		font.draw(batchGui, "Score = " + score, -WIDTH/2+5, HEIGHT / 2-17);
		
		batchGui.end();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		batchGui.dispose();
	}
}
