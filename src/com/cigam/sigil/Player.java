package com.cigam.sigil;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.cigam.sigil.Constants.Direction;

public class Player extends Entity {
	public String name = "John Smith";
	public CigamGame game;
	public Direction attackDirection;
	public int hurtTimer;
	
	public Player(CigamGame eg) {
		super();
		hurtTimer = 0;
        BodyDef fb = new BodyDef();
        fb.type = BodyType.DYNAMIC;
        fb.position = new Vec2(10, 10);
        fb.awake = true;
        fb.angle = 0f;
        fb.userData = this;
        this.body = game.world.createBody(fb);
        direction = Constants.Direction.NORTH;
        game = eg;
        this.health = Constants.FIRE_LIFE;
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
