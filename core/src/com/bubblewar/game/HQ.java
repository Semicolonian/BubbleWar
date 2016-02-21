package com.bubblewar.game;

import com.bubblewar.game.HexagonTile.TileType;

public class HQ extends HexagonTile{

	public HQ (float x, float y, float radius, TileType tileType,Fraction fraction) {
		super(x, y, radius, tileType, fraction);
		this.tileType = tileType;
	}
}
