package com.cigam.sigil;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import com.cigam.sigil.Constants.Direction;
import com.cigam.sigil.magic.MaterialDescriptor;

public class Player extends PhysicalEntity {
	public String name = "John Smith";
	public CigamGame game;
	public Direction attackDirection;
	
	//World world, MaterialDescriptor material, BodyDef bd, FixtureDef[] fds
	public Player(CigamGame eg, MaterialDescriptor mat, BodyDef b, FixtureDef[] fds) {
		super(eg.world, mat, b, fds);
		direction = Constants.Direction.NORTH;
        game = eg;
		restart();
	}

	public void restart() {
		attackDirection = Direction.SOUTH;
	}
}
