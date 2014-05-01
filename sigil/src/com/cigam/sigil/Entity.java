package com.cigam.sigil;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.cigam.sigil.Constants.Direction;

public abstract class Entity {
	SigilGame game;
	
	public Sprite sprite;
	public boolean active = true;
	public boolean visible = true;
	public float elapsedTime;
	public int plane;
	public float health;
    public Direction direction;
	
    public float damageFlash;
    
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
			damageFlash -= delta;
			if(damageFlash < 0 || damageFlash % 0.3 < 0.15)
				game.batch.setColor(Color.WHITE);
			else
				game.batch.setColor(Color.RED);
			if(plane == Constants.ETHEREAL_PLANE) {
				float r = game.batch.getColor().r;
				float g = game.batch.getColor().g;
				float b = game.batch.getColor().b;
				game.batch.setColor(r, g, b, 0.5f);
			}

			if(sprite!=null) {
				sprite.setColor(game.batch.getColor());
				sprite.draw(game.batch);
				sprite.setColor(Color.WHITE);
			}
		}
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
			if(damageFlash < 0) {
				health -= dmg;
				if(health < 0) {
					kill();
				}
			}
			if(dmg > 0)
				damageFlash = 1.0f;
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