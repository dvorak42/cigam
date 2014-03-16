package com.cigam.sigil;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Vector2f;

import com.cigam.sigil.graphics.Assets;
import com.cigam.sigil.graphics.DirectedImage;
import com.cigam.sigil.screens.BattleScreen;
import com.cigam.sigil.screens.Screen;


public class CigamGame extends BasicGame
{
	public Player player;
	public Screen current;
	
	public int timeout = 0;
	
	public CigamGame() {
		super("Hello World");
	}
 
	public void transition(int state) {
		if(current == null)
			return;
		current = current.transition(state);
		timeout = 500;
		player.active = true;
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		Assets.load();
		player = new Player(this);
		player.position = new Vector2f(32, 32);
		player.img = new DirectedImage(Assets.loadImage("art/player.png"));
		player.size = new Vector2f(Constants.TILE_SIZE * 0.9f, Constants.TILE_SIZE * 0.9f);
		System.out.println("Pre current");
		current = new BattleScreen(this, null);
		System.out.println("Post current");
	}
 
	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		if(gc.getInput().isKeyDown(Input.KEY_ESCAPE))
			System.exit(0);
		timeout -= delta;
		if(timeout > 0)
			return;
		
		current.update(gc, delta);
	}
 
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		current.render(gc, g);
	}
}