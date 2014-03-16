package com.cigam.sigil;

import org.jbox2d.dynamics.Body;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import com.cigam.sigil.Constants.Direction;
import com.cigam.sigil.graphics.DirectedImage;
import com.cigam.sigil.graphics.TileMap;

public abstract class Entity {
	public Body body;
	public Direction direction;
	public DirectedImage img;
	public Vector2f size;
	public int health;
	boolean active = true;
	public int invulnerable = 0;
	public int deathTimer = 0;
	public boolean moving = true;
	
	public Entity() {
		direction = Constants.Direction.SOUTH;
		size = new Vector2f();
		resetHealth();
		img = new DirectedImage();
	}
	
	public abstract int maxHealth();
	
	public boolean active()
	{
		return active;
	}
	
	public void setActive(boolean a)
	{
		if(!a)
			deathTimer = 1000;
		active = a;
	}
	
	public void damage(int am) {
		health -= am;
		if(health <= 0)
			setActive(false);
	}
	
	public void resetHealth() {
		health = maxHealth();
	}

	
	public void update(int dt) {
		if(moving)
			img.update(dt);
		
		if(deathTimer > 0)
			deathTimer -= dt;
	}
	
	public void draw(Graphics g) {
		if(active)
			img.draw(body.getPosition().x - size.x / 2, body.getPosition().y - size.y / 2, size.x, size.y, direction);
	}
        
	public void kill()
	{
		this.health = 0;
		active = false;
	}

	
}