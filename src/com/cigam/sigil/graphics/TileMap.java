package com.cigam.sigil.graphics;

import org.newdawn.slick.geom.Vector2f;

import com.cigam.sigil.Constants;

public class TileMap {
	public Tile[][] tiles;
	
	public TileMap(int x, int y) {
		tiles = new Tile[x][y];
	}
	
	public void draw(int offx, int offy) {
		for(int x = 0; x < tiles.length; x++) {
			for(int y = 0; y < tiles[0].length; y++) {
				tiles[x][y].draw(offx + x * Constants.TILE_SIZE, offy + y * Constants.TILE_SIZE);
			}
		}
	}	
	
	public boolean valid(int offx, int offy, Vector2f v1, Vector2f v2) {
		Vector2f v3 = new Vector2f(v1.x, v2.y);
		Vector2f v4 = new Vector2f(v2.x, v1.y);
		return validPoint(offx, offy, v1) && validPoint(offx, offy, v2) && 
				validPoint(offx, offy, v3) && validPoint(offx, offy, v4);
	}
	
	public boolean validPoint(int offx, int offy, Vector2f v1) {
		int ix = (int)((v1.x - offx) / Constants.TILE_SIZE);
		int iy = (int)((v1.y - offy) / Constants.TILE_SIZE);
		if(ix >= 0 && ix < tiles.length && iy >= 0 && iy < tiles[0].length) {
			return !tiles[ix][iy].solid;
		}
		return true;
	}
}
