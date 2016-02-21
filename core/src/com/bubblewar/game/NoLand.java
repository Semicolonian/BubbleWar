package com.bubblewar.game;

import com.bubblewar.game.HexagonTile.TileType;

public class NoLand extends HexagonTile{

	public NoLand (float x, float y, float radius, TileType tileType,Fraction fraction) {
		super(x, y, radius, tileType, fraction);
		this.tileType = tileType;
	}
}
