package com.cigam.sigil;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import com.cigam.sigil.Constants.Direction;
import com.cigam.sigil.graphics.DirectedImage;

public abstract class Entity {
	public DirectedImage img;
	public Vector2f position;
	public Direction direction;
	boolean active = true;
	boolean visible = true;
	
	public Entity() {
		position = new Vector2f();
		direction = Constants.Direction.SOUTH;
		img = new DirectedImage();
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
	
	public void update(int dt) {
		if(active) {
			img.update(dt);
		}
	}
	
	public void setPosition(Vector2f pos) {
		position = pos;
	}

	public Vector2f getPosition() {
		return position;
	}
	
	public void setDirection(Constants.Direction dir) {
		direction = dir;
	}
	
	public Constants.Direction getDirection() {
		return direction;
	}
	
	public void draw(Graphics g) {
		Vector2f position = getPosition();
		if(visible)
			img.draw(position.x - img.width / 2, position.y - img.height / 2, img.width, img.height, direction);
	}
        
	public void kill()
	{
		setActive(false);
	}
}