package com.bubblewar.game;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;


public class GameObject {
	public Vector2 position;
	public Circle bounds;
	public Fraction fraction;

	public GameObject (float x, float y, float radius, Fraction fraction) {
		this.position = new Vector2(x, y);
		//kreis tangentiert hexagon seiten nicht richtig? radius àndern oder susch öppis
		this.bounds = new Circle( x,  y, radius);
		this.fraction=fraction;
	}
}
