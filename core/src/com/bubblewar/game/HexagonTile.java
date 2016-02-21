package com.bubblewar.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class HexagonTile extends GameObject {
	
	
	
	public Color landColor= new Color((float)0.8,(float)0.8,(float)0.8,1);
	public Color noLandColor= new Color((float)0.1,(float)0.1,(float)0.1,1);
	public Color HQColor= new Color((float)0.5,(float)0.5,(float)0.5,1);
	public Color mainMenuColor= new Color((float)0.7,(float)0.7,(float)0.7,1);
	
	public enum TileType{
		LAND,
		NOLAND,
		HQ
	}
	
	public Vector2[] vertices = new Vector2[6]; 
	public TileType tileType;

	Color color;

	public HexagonTile (float x, float y, float radius, TileType tileType, Fraction fraction) {
		super(x, y, radius, fraction);
		this.tileType = tileType;
		

		vertices[0] = new Vector2((float) (x -(radius*0.5))	,(float) (y + ( (Math.sqrt(3)*0.5)*radius) ) );
		vertices[1] = new Vector2((float) (x +(radius*0.5))	,(float) (y + ( (Math.sqrt(3)*0.5)*radius) ) );
		vertices[2] = new Vector2(		   x + radius		,y);
		vertices[3] = new Vector2((float) (x +(radius*0.5))	,(float) (y-( (Math.sqrt(3)*0.5)*radius)) ) ;
		vertices[4] = new Vector2((float) (x -(radius*0.5))	,(float) (y-( (Math.sqrt(3)*0.5)*radius)) ) ;
		vertices[5] = new Vector2(		   x - radius		,y);
	}
	
	public void setTileType(TileType temp){
		this.tileType=temp;
		switch(tileType){
			case LAND:
				this.color=landColor;
				break;
			case NOLAND:
				this.color=noLandColor;
				break;
			case HQ:
				this.color=HQColor;
				break;
		}
	}

}
