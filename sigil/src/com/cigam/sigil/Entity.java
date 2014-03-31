package com.cigam.sigil;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
	SigilGame game;
	
	public Sprite sprite;
	boolean active = true;
	boolean visible = true;
	float elapsedTime;
	
	public Entity(SigilGame g, Sprite s) {
		game = g;
		sprite = s;
		elapsedTime = 0.0f;
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
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
	
	public void render() {
		if(active) {
			elapsedTime += Gdx.graphics.getDeltaTime();
		}
		
		sprite.draw(game.batch);
	}
	
	public void setPosition(Vector2 pos) {
		//TODO Don't want dimensions because origin is center?
		sprite.setPosition(pos.x - sprite.getWidth() / 2, pos.y - sprite.getHeight() / 2);
	}

	public Vector2 getPosition() {
		//TODO Don't want dimensions because origin is center?
		return new Vector2(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight() / 2);
	}
	        
	public void setSize(Vector2 size) {
		sprite.setSize(size.x, size.y);
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
	}

	public Vector2 getSize() {
		return new Vector2(sprite.getWidth(), sprite.getHeight());
	}
	//TODO make sure this is RAD and not degrees
	public void setRotation(float r) {
		sprite.setRotation(r);
	}

	public float getRotation() {
		return sprite.getRotation();
	}
	
	public void rotate(float dr) {
		setRotation(getRotation() + dr);		
	}
	        
	public void kill()
	{
		setActive(false);
	}
}