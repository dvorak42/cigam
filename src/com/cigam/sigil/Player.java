package com.cigam.sigil;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import com.cigam.sigil.Constants.Direction;

public class Player extends PhysicalEntity {
	public String name = "John Smith";
	public CigamGame game;
	public Direction attackDirection;
	
	public Player(CigamGame eg) {
		super(eg.world);
		
		BodyDef bd = new BodyDef();
		bd.active = true;
		bd.type = BodyType.DYNAMIC;
		bd.userData = this;
		bd.angle = 0;
		bd.awake = true;
		bd.position = new Vec2(0, 0);
		
		FixtureDef fd = new FixtureDef();
		PolygonShape ps = new PolygonShape();
		ps.setAsBox(64, 32);
		fd.shape = ps;
		fd.density = 1;
		updateBody(bd, new FixtureDef[]{fd});
		direction = Constants.Direction.NORTH;
        game = eg;
		restart();
	}

	public void restart() {
		attackDirection = Direction.SOUTH;
	}
}
