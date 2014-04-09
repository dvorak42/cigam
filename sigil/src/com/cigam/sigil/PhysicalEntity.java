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
import com.cigam.sigil.magic.MaterialDescriptor;
import com.cigam.sigil.magic.SpellDescriptor;
import com.cigam.sigil.magic.SpellEffect;
import com.cigam.sigil.screens.AdventureScreen;

public abstract class PhysicalEntity extends Entity {
	public AdventureScreen screen;
	public Body body;
	public MaterialDescriptor mat;
	public Vector2 modelOrigin = Vector2.Zero;
	public ArrayList<PhysicalEntity> boundEntities;
	public ArrayList<PhysicalEntity> imbuedEntities;
	public float totalManaCapacity;
	public float totalManaWeight;
	public float initManaCapacity;
	public float totalManaBound;
	
	public PhysicalEntity(SigilGame g, Sprite s, AdventureScreen a, MaterialDescriptor material) {
		super(g, s);
		this.screen = a;
		mat = material;
		initBody(a.world);
		boundEntities = new ArrayList<PhysicalEntity>();
		imbuedEntities  = new ArrayList<PhysicalEntity>();
		initManaCapacity = mat.manaCapacityFactor*this.body.getMass();
		totalManaCapacity = initManaCapacity;
		//System.out.println(this);
		//System.out.println(totalManaCapacity);
		totalManaBound = 0;
		totalManaWeight = mat.manaDensityFactor*this.body.getMass();
		//System.out.println("Mana stats for " + this + " are as follows: ");
		//System.out.println("body mass is " + this.body.getMass());
		//System.out.println("totalMana")
	}
	
	public PhysicalEntity(SigilGame g, Sprite s, AdventureScreen a, SpellDescriptor sd) {
		super(g, s);
		this.screen = a;
		mat = sd.mat;
		initBody(a.world, sd);
		boundEntities = new ArrayList<PhysicalEntity>();
		imbuedEntities  = new ArrayList<PhysicalEntity>();
		totalManaCapacity = mat.manaCapacityFactor*this.body.getMass();
		totalManaWeight = mat.manaDensityFactor*this.body.getMass();
		//System.out.println("Mana stats for " + this + " are as follows: ");
		//System.out.println("body mass is " + this.body.getMass());
		//TODO: proceduraly generate magic
		//TODO: Rotation direction = Constants.Direction.
	}
	//This should never be called
	public void initBody(World w) {
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
		BodyDef bd = new BodyDef();
		if(sd != null) {
			//sSystem.out.println(sd.position);
			bd.position.set(sd.position);
		}
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
	
	public void bind(PhysicalEntity p, float bindingValue){
		totalManaCapacity += bindingValue;
		if(totalManaCapacity > totalManaBound+p.totalManaWeight&&initManaCapacity*2 > (totalManaBound+p.totalManaWeight)){
			totalManaBound+=p.totalManaWeight;
			boundEntities.add(p);
			p.setActive(false);
			p.setVisible(false);
		} else {
			System.out.println(p.totalManaWeight + " was totalManaWeight and " + totalManaCapacity + " was totalManaCapacity");
			this.kill();
		}
		System.out.println("Binding " + p + " into " + this);
		System.out.println("totalManaCapacity is now " + totalManaCapacity);
	}
	
	public void unbind(PhysicalEntity p, float bindingValue){
		if(boundEntities.contains(p)){
			boundEntities.remove(p);
			p.body.setTransform(this.body.getWorldCenter(), p.body.getAngle());
			p.body.setLinearVelocity((float) (Math.random()*50), (float) (Math.random()*50));
			p.setActive(true);
			p.setVisible(true);
			totalManaCapacity -= bindingValue;
			totalManaBound-= p.totalManaWeight;
		}
	}
	public void kill(){
		this.setActive(false);
		this.mat.onDestroy(screen);
		for(int i = 0; i < boundEntities.size(); i++){
			unbind(boundEntities.get(i),0);
		}
		//System.out.println(screen.world);
		screen.world.destroyBody(this.body);
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
	public void render() {
		if(this.visible){
			Vector2 spritePos = body.getPosition().sub(modelOrigin);
			
			sprite.setPosition(spritePos.x, spritePos.y);
			sprite.setOrigin(modelOrigin.x, modelOrigin.y);
			sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);

		super.render();
		}
	}
}
