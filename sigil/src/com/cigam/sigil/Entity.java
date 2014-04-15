package com.cigam.sigil;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
	SigilGame game;
	
	public Sprite sprite;
	boolean active = true;
	public boolean visible = true;
	public float elapsedTime;
	public int plane;
	public float health;
	
	public Entity(SigilGame g, Sprite s) {
		game = g;
		sprite = s;
		elapsedTime = 0.0f;
		plane = 1;
		health = -1;
	}
	
	public boolean active() {
		return active;
	}
	
	public void setActive(boolean a) {
		active = a;
	}
	
	public boolean visible() {
		return visible;
	}
	
	public void setVisible(boolean v) {
		visible = v;
	}
	
	public void render(float delta) {
		if(active) {
			elapsedTime += delta;
		}
		if(plane == Constants.ETHEREAL_PLANE)
			sprite.draw(game.batch, 0.5f);
		else
			sprite.draw(game.batch);
	}
	
	public void setPosition(Vector2 pos) {
		sprite.setPosition(pos.x, pos.y);
	}

	public Vector2 getPosition() {
		return new Vector2(sprite.getX(), sprite.getY());
	}
	        
	public void setSize(Vector2 size) {
		sprite.setSize(size.x, size.y);
	}

	public Vector2 getSize() {
		return new Vector2(sprite.getWidth(), sprite.getHeight());
	}
	
	public void setRotation(float r) {
		sprite.setRotation(r);
	}

	public float getRotation() {
		return sprite.getRotation();
	}
	
	public void damage(float dmg) {
		if(health != -1) {
			health -= dmg;
			if(health < 0) {
				kill();
			}
		}
	}
	
	public void heal(float heal) {
		if(health != -1) {
			health += heal;
		}
	}
	        
	public void kill()
	{
		setActive(false);
	}
}