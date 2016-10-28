package com.fjfj.prototype;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;

public class MainGame extends ApplicationAdapter {

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	
	ModelBatch batch;
	Environment env;
	PerspectiveCamera cam;
	
	OrthographicCamera camGui;
	SpriteBatch batchGui;
	BitmapFont font;
	
	AssetManager assets;


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

		world = new ModelInstance(assets.get("models/sphere/sphere.g3db", Model.class));
		world.transform.translate(0, -100, 0);
	
		camGui = new OrthographicCamera(WIDTH, HEIGHT);
		batchGui = new SpriteBatch();
		font = new BitmapFont();
		guiTex = assets.get("textures/gui.png", Texture.class);
		eyeTex = assets.get("textures/eye.png", Texture.class);
	}

	@Override
	public void render() {

		update();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT
				| (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));

		batch.begin(cam);

		batch.render(world, env);


		batch.end();

		drawGui();
	}

	private void update() {

	}

	private void drawGui() {
		batchGui.setProjectionMatrix(camGui.combined);
		
		batchGui.begin();
		
		batchGui.draw(guiTex, -WIDTH/2, -HEIGHT/2, WIDTH, HEIGHT);
		
		
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
