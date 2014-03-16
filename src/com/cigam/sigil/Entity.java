package com.cigam.sigil;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import com.cigam.sigil.Constants.Direction;
import com.cigam.sigil.graphics.DirectedImage;
import com.cigam.sigil.graphics.TileMap;

public abstract class Entity {
	public Vector2f position;
	public Direction direction;
	public DirectedImage img;
	public Vector2f size;
	public int health;
	boolean active = true;
	public int invulnerable = 0;
	public int deathTimer = 0;
	public boolean moving = true;
	
	public Entity() {
		position = new Vector2f();
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
			img.draw(position.x - size.x / 2, position.y - size.y / 2, size.x, size.y, direction);
	}
        
	public void kill()
	{
		this.health = 0;
		active = false;
	}

	public void move(Vector2f mov, int lx, int ly, int mx, int my) {
		if(!active)
			return;
		
		if(mov.length() > 1.0f)
		{
			direction = Helper.directionTo(position, position.copy().add(mov));
			moving = true;
		}
		else
			moving = false;
		position = Helper.bound(position.add(mov), lx + size.x / 2, ly + size.y / 2, mx - size.x / 2, my - size.y / 2);
	}
	
	public void move(Vector2f mov, int lx, int ly, int mx, int my, TileMap map) {
		Vector2f cmov = mov.copy();		
		
		Vector2f nPos = new Vector2f(position.x + cmov.x, position.y + cmov.y);
		if(map.valid(0, 0, new Vector2f(nPos.x - size.x / 2 + 10, nPos.y - size.y / 2 + 10), new Vector2f(nPos.x + size.x / 2 - 10, nPos.y + size.y / 2 - 10)))
		{
			move(mov, lx, ly, mx, my);
		}
	}
}