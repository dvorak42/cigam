package com.cigam.sigil;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.cigam.sigil.Constants.Direction;

public class Player extends Entity {
	public String name = "John Smith";
	public CigamGame game;
	public Direction attackDirection;
	public int hurtTimer;
	public int MOLOTOV_DEPTH = 0;

	public Player(CigamGame g) {
		super();
		game = g;
		hurtTimer = 0;
		restart();
	}

	public void restart() {
		health = maxHealth();
		attackDirection = Direction.SOUTH;
	}

	@Override
	public int maxHealth() {
		return Constants.MAX_HEALTH;
	}
	
	@Override
	public void update(int dt) {
		if(hurtTimer > 0)
			hurtTimer -= dt;
	}
	
	@Override
	public void draw(Graphics g){
		super.draw(g);
		
		float startX = position.x - size.x / 2;
		float startY = position.y + size.y / 2;
		float offY = 4;
		float maxWidth = size.x;
		g.setColor(Color.red);
        g.fillRect(startX, startY, maxWidth, offY);
        g.setColor(Color.green);
        g.fillRect(startX, startY, (1.0f * health / maxHealth()) * maxWidth, offY);

	}
}
