package com.cigam.sigil;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.cigam.sigil.magic.MaterialDescriptor;

public class SolidProjectile extends PhysicalEntity {
    public SigilGame game;
    public Entity parent;
    
    public SolidProjectile(SigilGame eg, Sprite sprite, World world, MaterialDescriptor mat, float angle, Entity parent, float density, Vector2 vel)//TODO move Body and fixture defs to constants 
    {
        super(eg, sprite, world, mat);
        game = eg;
        this.parent = parent;
        initBody(angle, density, vel);
    }
    
	public void initBody(float angle, float density, Vector2 vel) {
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DynamicBody;
		bd.angle = angle;
		bd.bullet = true;
		bd.position.set(parent.getPosition().cpy().add(Utils.angleToVector(angle).scl(50.0f)));

		body = world.createBody(bd);

		FixtureDef fd = new FixtureDef();
		fd.density = density; 

	    Utils.mainBodies.attachFixture(body, "fireball", fd, sprite.getWidth());
	    modelOrigin = Utils.mainBodies.getOrigin("fireball", sprite.getWidth());

		body.setUserData(this);
		body.setLinearVelocity(vel);
	}
}
