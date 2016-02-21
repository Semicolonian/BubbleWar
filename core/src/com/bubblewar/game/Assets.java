package com.bubblewar.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
	public static Texture background;
	public static TextureRegion backgroundRegion;
	public static Texture hexagonland;
	public static TextureRegion hexagonlandRegion;
	public static Texture hexagonnoland;
	public static TextureRegion hexagonnolandRegion;
	public static Texture hq;
	public static TextureRegion hqRegion;
	public static Texture slider_background;
	public static TextureRegion slider_backgroundRegion;
	public static Texture slider_knob;
	public static Texture dialog_background;
	
	public static BitmapFont font;

	

	public static Texture loadTexture (String file) {
		return new Texture(Gdx.files.internal(file));
	}

	public static void load () {
		background = loadTexture("data/background.png");
		backgroundRegion = new TextureRegion(background, 0, 0, 1280, 720);
		hexagonland = loadTexture("data/hexagonland.png");
		hexagonlandRegion = new TextureRegion(hexagonland, 0, 0, 640, 555);
		hexagonnoland = loadTexture("data/hexagonnoland.png");
		hexagonnolandRegion = new TextureRegion(hexagonnoland, 0, 0, 640, 555);
		hq = loadTexture("data/hq.png");
		hqRegion = new TextureRegion(hq, 0, 0, 640, 555);
		
		
		slider_background = loadTexture("data/slider_background.png");
		slider_backgroundRegion = new TextureRegion(slider_background, 0, 0, 800, 20);
	//	slider_background = new Texture(Gdx.files.internal("data/slider_background.png"));
		slider_knob = new Texture(Gdx.files.internal("data/slider_knob.png"));
		dialog_background = new Texture(Gdx.files.internal("data/dialog_background.png"));

		

		font = new BitmapFont(Gdx.files.internal("data/font.fnt"), Gdx.files.internal("data/font.png"), false);

		
	}


}