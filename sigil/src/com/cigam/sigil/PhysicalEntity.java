package com.cigam.sigil;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.cigam.sigil.magic.MaterialDescriptor;
import com.cigam.sigil.magic.SpellDescriptor;

public abstract class PhysicalEntity extends Entity {
	public World world;
	public Body body;
	public MaterialDescriptor mat;
	
	public PhysicalEntity(SigilGame g, Sprite s, World world, MaterialDescriptor material) {
		super(g, s);
		this.world = world;
		mat = material;
	}
	
	public PhysicalEntity(SigilGame g, Sprite s, World world, SpellDescriptor sd) {
		super(g, s);
		mat = sd.mat;
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
		CircleShape circle = new CircleShape();
		circle.setRadius(10);
		fd.shape = circle;
		fd.density = 0.1f; 
		//TODO: filter
		fd.isSensor = true;

		body.createFixture(fd);
		body.setUserData(this);
		circle.dispose();
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
}
