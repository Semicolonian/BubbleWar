package com.bubblewar.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;


public class Hexagon extends GameObject {

	public Vector2[] vertices = new Vector2[6]; 
	
	float x;
	float y;
	float radius;
	Color color;
	
	public Hexagon (float x, float y, float radius) {
		super(x, y, radius);
		
		this.x= x;
		this.y =y;
		this.radius =radius;
		
		vertices[0] = new Vector2((float) (x -(radius*0.5))	,(float) (y + ( (Math.sqrt(3)*0.5)*radius) ) );
		vertices[1] = new Vector2((float) (x +(radius*0.5))	,(float) (y + ( (Math.sqrt(3)*0.5)*radius) ) );
		vertices[2] = new Vector2(		   x + radius		,y);
		vertices[3] = new Vector2((float) (x +(radius*0.5))	,(float) (y-( (Math.sqrt(3)*0.5)*radius)) ) ;
		vertices[4] = new Vector2((float) (x -(radius*0.5))	,(float) (y-( (Math.sqrt(3)*0.5)*radius)) ) ;
		vertices[5] = new Vector2(		   x - radius		,y);
	}
	
	
	
	
}