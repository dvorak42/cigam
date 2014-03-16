package com.cigam.sigil;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.geom.Vector2f;

import com.cigam.sigil.graphics.DirectedImage;

public class PhysicalEntity extends Entity {
	public World world;
	public Body body;
	
	public PhysicalEntity(World world) {
		this(world, null, null);
	}
	
	public PhysicalEntity(World world, BodyDef bd, FixtureDef[] fds) {
		this.world = world;
		updateBody(bd, fds);
		position = Helper.v2v(body.getPosition());
		direction = Constants.Direction.SOUTH;
		img = new DirectedImage();
	}
	
	public void setImage(DirectedImage img){
		this.img = img;
	}
	
	public void updateBody(BodyDef bd, FixtureDef[] fds) {
		if(bd != null) {
			body = world.createBody(bd);
		} else {
			body = world.createBody(new BodyDef());
		}
		if(fds != null) {
			for(FixtureDef fd : fds)
				body.createFixture(fd);
		}
	}
	
	public boolean active() {
		active = body.isActive();
		return super.active();
	}
	
	public void setActive(boolean a) {
		active = a;
		body.setActive(active);
	}
	
	public void setPosition(Vector2f pos) {
		position = pos;
		body.setTransform(Helper.v2v(pos), body.getAngle());
	}

	public Vector2f getPosition() {
		position = Helper.v2v(body.getPosition());
		return position;
	}
	
	public void setDirection(Constants.Direction dir) {
		direction = dir;
		body.setTransform(body.getPosition(), Helper.directionToAngle(dir));
	}
	
	public Constants.Direction getDirection() {
		direction = Helper.angleToDirection(body.getAngle());
		return direction;
	}
	

	
	public void update(int dt) {
		if(active) {
			img.update(dt);
			position = Helper.v2v(body.getPosition());
			direction = Helper.angleToDirection(body.getAngle());
		}
	}
}
