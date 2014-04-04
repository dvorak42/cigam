package com.cigam.sigil;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.cigam.sigil.magic.MaterialDescriptor;
import com.cigam.sigil.magic.SpellDescriptor;

public abstract class PhysicalEntity extends Entity {
	public World world;
	public Body body;
	public MaterialDescriptor mat;
	Vector2 modelOrigin = Vector2.Zero;
	
	public PhysicalEntity(SigilGame g, Sprite s, World world, MaterialDescriptor material) {
		super(g, s);
		this.world = world;
		mat = material;
	}
	
	public PhysicalEntity(SigilGame g, Sprite s, World world, SpellDescriptor sd) {
		super(g, s);
		mat = sd.mat;
		initBody(world, sd);
		//TODO: proceduraly generate magic
		//TODO: Rotation direction = Constants.Direction.
	}
	//This should never be called
	public void initBody(World w) {
		world = w;
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DynamicBody;

		body = w.createBody(bd);
		
		FixtureDef fd = new FixtureDef();
		fd.density = 0.1f; 
		//TODO: filter
		fd.isSensor = true;

	    Utils.mainBodies.attachFixture(body, "fireball", fd, sprite.getWidth());
	    modelOrigin = Utils.mainBodies.getOrigin("fireball", sprite.getWidth());

	    body.setUserData(this);
	}
	//TODO: needs work translating sd into box2d. Also should live in a subclass
	public void initBody(World w, SpellDescriptor sd) {
		setPosition(sd.position);
		world = w;
		BodyDef bd = new BodyDef();
		if(sd != null)
			bd.position.set(sd.position);
		bd.type = BodyType.DynamicBody;

		body = w.createBody(bd);
		
		FixtureDef fd = new FixtureDef();
		if(sd != null)
			fd.shape = sd.shape;
		fd.density = 0.1f; 
		//TODO: filter
		fd.isSensor = true;

		body.createFixture(fd);
		body.setUserData(this);
	}
	
	public boolean active() {
		active = body.isActive();
		return super.active();
	}
	
	public void setActive(boolean a) {
		active = a;
		body.setActive(active);
	}

	@Override
	public void setPosition(Vector2 pos) {
		body.setTransform(pos, body.getAngle());
	}

	@Override
	public Vector2 getPosition() {
		return body.getPosition();
	}
	        
	@Override
	public void setSize(Vector2 size) {
		sprite.setSize(size.x, size.y);
	}

	@Override
	public Vector2 getSize() {
		return new Vector2(sprite.getWidth(), sprite.getHeight());
	}
	
	@Override
	public void setRotation(float r) {
		body.setTransform(body.getPosition(), r);
	}

	@Override
	public float getRotation() {
		return body.getAngle();
	}

	@Override
	public void render() {
		Vector2 spritePos = body.getPosition().sub(modelOrigin);
		
		sprite.setPosition(spritePos.x, spritePos.y);
		sprite.setOrigin(modelOrigin.x, modelOrigin.y);
		sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);

		super.render();
	}
}
