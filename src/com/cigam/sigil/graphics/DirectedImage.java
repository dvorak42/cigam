package com.cigam.sigil.graphics;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Image;

import com.cigam.sigil.Constants;
import com.cigam.sigil.Constants.Direction;

public class DirectedImage {
	public Map<Direction, Image> imgs;
	
	public DirectedImage()
	{
		imgs = new HashMap<Constants.Direction, Image>();
	}

	public DirectedImage(Image d)
	{
		this(d, new Image[0]);
	}
	
	public DirectedImage(Image d, Image[] a)
	{
		this();
		imgs.put(Constants.Direction.SOUTH, d);
		for(int i = 0; i < a.length; i++)
		{
			if(a[i] != null)
				imgs.put(Constants.Direction.values()[2 * i], a[i]);
		}
	}

	public void draw(float x, float y, float w, float h, Constants.Direction dir)
	{
		
		switch(dir)
		{
		case NORTH_EAST: dir = Constants.Direction.NORTH; break;
		case NORTH_WEST: dir = Constants.Direction.NORTH; break;
		case SOUTH_EAST: dir = Constants.Direction.SOUTH; break;
		case SOUTH_WEST: dir = Constants.Direction.SOUTH; break;
		}
		
		if(imgs.containsKey(dir))
			imgs.get(dir).draw(x, y, w, h);
		else
			imgs.get(Constants.Direction.SOUTH).draw(x, y, w, h);
	}
	
	public void update(int dt)
	{
	}
}
