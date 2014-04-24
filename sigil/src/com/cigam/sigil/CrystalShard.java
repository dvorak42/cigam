package com.cigam.sigil;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.cigam.sigil.magic.MaterialDescriptor;
import com.cigam.sigil.screens.AdventureScreen;

public class CrystalShard extends PhysicalEntity {
	Vector2 position;
	public Vector2 destination;

	public CrystalShard(SigilGame eg, Sprite sprite, AdventureScreen a, MaterialDescriptor mat, Vector2 pos) {
        super(eg, sprite, a, mat);
        health = 10000000;
        position = pos;
        initEntity();
	}

    @Override
	public void initBody() {
		BodyDef bd = new BodyDef();
		bd.type = BodyType.KinematicBody;
		bd.position.set(position.cpy().sub(new Vector2(sprite.getWidth() / 2, sprite.getHeight() / 2)));
		
		body = screen.world.createBody(bd);

		FixtureDef fd = new FixtureDef();
		fd.density = 10; 

	    Utils.mainBodies.attachFixture(body, "fireball", fd, sprite.getWidth());
	    modelOrigin = Utils.mainBodies.getOrigin("fireball", sprite.getWidth());

		body.setUserData(this);
	}
}
