package com.cigam.sigil.screens;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import com.cigam.sigil.CigamGame;
import com.cigam.sigil.graphics.Assets;

public class PauseScreen extends Screen {

	private Image pauseText;
	
	public PauseScreen(CigamGame game, Screen current) {
		super(game, current);
		this.cleanRestart();
	}
	
	@Override
	public Screen transition(int state) {
		return parent;
	}
	
	@Override
	public void init() throws SlickException {
		pauseText = Assets.loadImage("art/screens/pause.png");
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
    {
		parent.render(gc, g);
		g.setColor(new Color(0,0,0,128));
		g.fillRect(0, 0, gc.getWidth(), gc.getHeight());
		pauseText.draw((gc.getWidth()-pauseText.getWidth())/2, (gc.getHeight()-pauseText.getHeight())/2,pauseText.getWidth(), pauseText.getHeight());
    }
	
	@Override
	public void update(GameContainer gc, int dt) throws SlickException {
		Input in = gc.getInput();
		if(in.isKeyDown(Input.KEY_SPACE)){
			game.transition(-1);
		}
	}
}
