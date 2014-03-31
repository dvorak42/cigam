package com.cigam.sigil;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.cigam.sigil.magic.MaterialDescriptor;

public class Player extends PhysicalEntity {
	public String name = "John Smith";
	
	//World world, MaterialDescriptor material, BodyDef bd, FixtureDef[] fds
	//TODO: Don't need to pass material descriptor, should always be selfMat
	public Player(SigilGame g, Sprite s, World world, MaterialDescriptor mat) {
		super(g, s, world, mat);
		initBody();
	}
	
	public void initBody() {
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DynamicBody;
		bd.fixedRotation = true;
		bd.linearDamping = 1f;

		body = world.createBody(bd);
		
		FixtureDef fd = new FixtureDef();
		PolygonShape ps = new PolygonShape();
		ps.setAsBox(26, 28);
		fd.shape = ps;
		fd.density = 0.1f; 
		//TODO: filter

		body.createFixture(fd);
		body.setUserData(this);
		
		ps.dispose();
	}

}
