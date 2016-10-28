package com.fjfj.prototype;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Model;

public class Assets {
	public static AssetManager assets;
	
	public static void init(){
		assets = new AssetManager();
		assets.load("models/monster/monster.obj", Model.class);
		assets.load("models/sphere/sphere.g3db", Model.class);
		assets.load("textures/gui.png", Texture.class);
		assets.load("textures/eye.png", Texture.class);
		assets.load("textures/mask.png", Texture.class);
		while(assets.update())
			;
	}
	
}
