package com.cigam.sigil;

import org.newdawn.slick.*;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import com.cigam.sigil.graphics.Assets;
import com.cigam.sigil.graphics.DirectedImage;
import com.cigam.sigil.screens.BattleScreen;
import com.cigam.sigil.screens.Screen;


public class CigamGame extends BasicGame
{
	public Player player;
	public Screen current;
	public World world;
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
		world = new World(new Vec2());
		player = new Player(this);
		player.img = new DirectedImage(Assets.loadImage("art/player.png"));
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
		//world.step(delta, Constants.VELOCITY_ITERS, Constants.POSITION_ITERS);
		current.update(gc, delta);
		System.out.println(delta);
	}
 
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		current.render(gc, g);
	}
}