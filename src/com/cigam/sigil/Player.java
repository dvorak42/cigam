package com.cigam.sigil;


import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import com.cigam.sigil.Constants.Direction;
import com.cigam.sigil.screens.BattleScreen;

public class Player extends PhysicalEntity {
	public String name = "John Smith";
	public Direction attackDirection;
	
	//World world, MaterialDescriptor material, BodyDef bd, FixtureDef[] fds
	public Player(BattleScreen b, MaterialDescriptor mat, BodyDef bd, FixtureDef[] fds) {
		super(b.world, mat, bd, fds);
		direction = Constants.Direction.NORTH;
		restart();
	}

	public void restart() {
		attackDirection = Direction.SOUTH;
	}
}
