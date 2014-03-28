package com.cigam.sigil.screens;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.cigam.sigil.CigamGame;
import com.cigam.sigil.Player;

public class Screen {
	public CigamGame game;
	public Player player;
	public Screen parent;
	public Image background;
	
	public Screen transition(int state) {
		return parent;
	}
	
	public Screen()
	{
		
	}

	public Screen(CigamGame eg, Screen p) {
		game = eg;
		parent = p;
	}
	
	public void cleanRestart()
	{
		try {
			init();
		} catch(SlickException e) {
			e.printStackTrace();
		}
		
	}
	
	public void init() throws SlickException {
		restart();
	}
	
	public void restart() throws SlickException {		
	}
	
	public void update(GameContainer gc, int dt) throws SlickException {	
	}
	
	public void render(GameContainer gc, Graphics g) throws SlickException
    {
		if(background != null)
			background.draw(0, 0, gc.getWidth(), gc.getHeight());
    }
}
