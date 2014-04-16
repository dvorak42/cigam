package com.cigam.sigil;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.cigam.sigil.magic.Material;
import com.cigam.sigil.magic.MaterialDescriptor;
import com.cigam.sigil.magic.SpellDescriptor;
import com.cigam.sigil.magic.SpellEffect;
import com.cigam.sigil.screens.AdventureScreen;

public abstract class PhysicalEntity extends Entity {
	public AdventureScreen screen;
	public Body body;
	public World world;
	public Vector2 modelOrigin = Vector2.Zero;
	public MaterialDescriptor mDesc;
	public Material material;
	
	public PhysicalEntity(SigilGame g, Sprite s, AdventureScreen screen) {
		super(g, s);
		this.screen = screen;
		this.world = screen.world;
	}
	
	public PhysicalEntity(SigilGame g, Sprite s, AdventureScreen a, MaterialDescriptor material) {
		this(g, s, a);
		mDesc = material;
	}
	
	public abstract void initBody();
	
	public void initEntity() {
		initBody();
		material = mDesc.createMaterial(body);
	}

	public void kill(){
		this.setActive(false);
		screen.destroy(this);
	}
	public boolean active() {
		active = body.isActive();
		return super.active();
	}
	
	public void setActive(boolean a) {
		active = a;
	}

	@Override
	public void setPosition(Vector2 pos) {
		body.setTransform(pos.cpy().sub(body.getWorldCenter()), body.getAngle());
	}

	@Override
	public Vector2 getPosition() {
		return body.getWorldCenter();
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
	public void render(float delta) {
		if(this.visible){
			Vector2 spritePos = body.getPosition().sub(modelOrigin);
			
			sprite.setPosition(spritePos.x, spritePos.y);
			sprite.setOrigin(modelOrigin.x, modelOrigin.y);
			sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);

			super.render(delta);
		}
	}
}
