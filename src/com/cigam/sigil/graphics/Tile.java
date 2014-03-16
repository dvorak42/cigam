package com.cigam.sigil.graphics;

import org.newdawn.slick.Image;

import com.cigam.sigil.Constants;

public class Tile {
	public Image img;
	public boolean solid;
	
	public Tile(Image i)
	{
		this(i, true);
	}
	
	public Tile(Image i, boolean sol) {
		img = i;
		solid = sol;
	}
	
	public void draw(int x, int y) {
		img.draw(x, y, Constants.TILE_SIZE, Constants.TILE_SIZE);
	}

}
