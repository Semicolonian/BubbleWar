package com.bubblewar.game;

import com.badlogic.gdx.math.Vector2;


public class DynamicGameObject extends GameObject {
	public Vector2 velocity;
	public Vector2 accel;

	public DynamicGameObject (float x, float y, float radius, Fraction fraction) {
		super(x, y, radius, fraction);
		velocity = new Vector2();
		accel = new Vector2();
	}
}

