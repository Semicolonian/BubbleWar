package com.bubblewar.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.bubblewar.game.Assets;

public class BubbleWar extends Game {
	SpriteBatch batcher;
	ShapeRenderer shapeRenderer;
	
	@Override
	public void create () {
		batcher = new SpriteBatch();
		Assets.load();
		shapeRenderer = new ShapeRenderer();
		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
