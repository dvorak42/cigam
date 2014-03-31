package com.cigam.sigil;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.cigam.sigil.magic.MaterialDescriptor;

public class Enemy extends PhysicalEntity {
	public SigilGame game;
	
	public Enemy(SigilGame eg, Sprite sprite, World world, MaterialDescriptor mat) {
		super(eg, sprite, world, mat);
        game = eg;
		initBody();
	}
	//TODO should have override flag?
	public void initBody() {
		Vector2 pos = new Vector2((int)(Math.random()*500)+100, (int)(Math.random()*500)+100);

		setPosition(pos);
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DynamicBody;
		bd.angle = 0;
		bd.fixedRotation = true;
		bd.position.set(pos);
		
		body = world.createBody(bd);
		
		FixtureDef fd = new FixtureDef();
		PolygonShape ps = new PolygonShape();
		ps.setAsBox(64, 32);
		fd.shape = ps;
		fd.density = 0.0001f; 

		body.createFixture(fd);
		body.setUserData(this);
		
		ps.dispose();
	}

	@Override
	public void render() {
		super.render();
		if(!active)
			return;
		
		body.applyForceToCenter(Utils.angleToVector(body.getAngle()).scl((float) (Constants.ENEMY_MOVE_SPEED/60f)), true);
		body.setAngularVelocity(MathUtils.random(MathUtils.PI * 2));
	}
}