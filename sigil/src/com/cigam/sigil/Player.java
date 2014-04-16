package com.cigam.sigil;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.cigam.sigil.magic.MaterialDescriptor;
import com.cigam.sigil.materials.SelfMat;
import com.cigam.sigil.screens.AdventureScreen;

public class Player extends PhysicalEntity {
	public String name = "John Smith";
	
	//World world, MaterialDescriptor material, BodyDef bd, FixtureDef[] fds
	//TODO: Don't need to pass material descriptor, should always be selfMat
	public Player(SigilGame g, Sprite s, AdventureScreen a) {
		super(g, s, a, new SelfMat());
		initEntity();
		health = Constants.DEFAULT_HEALTH;
	}

	@Override
	public void initBody() {
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DynamicBody;
		bd.fixedRotation = true;
		bd.linearDamping = 8f;

		body = screen.world.createBody(bd);
		
		FixtureDef fd = new FixtureDef();
		fd.density = 0.1f; 
	    Utils.mainBodies.attachFixture(body, "player", fd, sprite.getWidth());
	    modelOrigin = Utils.mainBodies.getOrigin("player", sprite.getWidth());

		body.setUserData(this);
	}
}
