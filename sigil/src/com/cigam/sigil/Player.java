package com.cigam.sigil;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.cigam.sigil.materials.SelfMat;
import com.cigam.sigil.screens.AdventureScreen;

public class Player extends PhysicalEntity {
	public String name = "John Smith";
	
	Vector2 nextTeleport;
	
	//World world, MaterialDescriptor material, BodyDef bd, FixtureDef[] fds
	public Player(SigilGame g, Sprite s,AdventureScreen a) {
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
		//TODO: filter

		body.setUserData(this);
	}

	@Override
	public void render(float dt) {
		if(nextTeleport != null) {
			setPosition(nextTeleport);
			nextTeleport = null;
		}
		super.render(dt);
	}
	
	public void gainShard(CrystalShard a) {
		nextTeleport = a.destination;
	}
}
