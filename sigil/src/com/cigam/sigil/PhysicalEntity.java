package com.cigam.sigil;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.cigam.sigil.magic.MaterialDescriptor;
import com.cigam.sigil.screens.AdventureScreen;

public abstract class PhysicalEntity extends Entity {
	public AdventureScreen screen;
	public Body body;
	public World world;
	public MaterialDescriptor mat;
	public Vector2 modelOrigin = Vector2.Zero;
	public ArrayList<PhysicalEntity> boundEntities;
	public ArrayList<PhysicalEntity> imbuedEntities;
	public float totalManaCapacity;
	public float totalManaWeight;
	public float initManaCapacity;
	public float totalManaBound;	
	public int autoDamage;
	public float autoDelay;
	
	public PhysicalEntity(SigilGame g, Sprite s, AdventureScreen screen) {
		super(g, s);
		this.screen = screen;
		this.world = screen.world;
		boundEntities = new ArrayList<PhysicalEntity>();
		imbuedEntities  = new ArrayList<PhysicalEntity>();
		autoDamage = 0;
		autoDelay = 0.0f;
		destructable = true;
	}
	
	public PhysicalEntity(SigilGame g, Sprite s, AdventureScreen a, MaterialDescriptor material) {
		this(g, s, a);
		mat = material;
		mat.manifestion = this;
		destructable = true;
	}
	
	public abstract void initBody();
	
	public void initEntity() {
		initBody();
		initManaCapacity = mat.manaCapacityFactor*this.body.getMass();
		totalManaCapacity = initManaCapacity;
		totalManaBound = 0;
		totalManaWeight = mat.manaDensityFactor*this.body.getMass();
		//System.out.println(this + " manaweight " + totalManaWeight);
		//System.out.println(this + " manacapacity " + initManaCapacity);
	}
	
	public void bind(PhysicalEntity p, float bindingValue){
		totalManaCapacity += bindingValue;
		System.out.println(this + " binding");
		if(totalManaCapacity > totalManaBound+p.totalManaWeight&&initManaCapacity*2 > (totalManaBound+p.totalManaWeight)){
			totalManaBound+=p.totalManaWeight;
			boundEntities.add(p);
			p.setActive(false);
			p.setVisible(false);
		} else {
			//System.out.println(p.totalManaWeight + " was totalManaWeight and " + totalManaCapacity + " was totalManaCapacity");
			p.body.setTransform(this.body.getWorldCenter(), p.body.getAngle());
			p.body.setLinearVelocity((float) (Math.random()*50), (float) (Math.random()*50));
			this.kill();
		}
		//System.out.println("Binding " + p + " into " + this);
		//System.out.println("totalManaCapacity is now " + totalManaCapacity);
	}
	
	public void unbind(PhysicalEntity p, float bindingValue){
		System.out.println(this + " unbinding");
		if(boundEntities.contains(p)){
			boundEntities.remove(p);
			p.body.setTransform(new Vector2((float)(this.body.getWorldCenter().x+Math.random()*5), (float)(this.body.getWorldCenter().y+Math.random()*5)), p.body.getAngle());
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
		if(!screen.toDestroy.contains(this, true))
			screen.toDestroy.add(this);
	}
	public boolean active() {
		active = body.isActive();
		return super.active();
	}
	
	public void setActive(boolean a) {
		active = a;
		body.setActive(a);
	}

	@Override
	public void setPosition(Vector2 pos) {
		body.setTransform(pos.cpy().sub(body.getWorldCenter()).add(body.getPosition()), body.getAngle());
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
		autoDelay -= delta;
		if(autoDelay <= 0) {
			damage(autoDamage);
			autoDelay = 1;
		}
		if(this.visible){
			Vector2 spritePos = body.getPosition().sub(modelOrigin);
			if(sprite!=null){
				sprite.setPosition(spritePos.x, spritePos.y);
				sprite.setOrigin(modelOrigin.x, modelOrigin.y);
				sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
			}
			super.render(delta);
		}
	}

	public void addAutoDamage(int d) {
		autoDamage += d;
		if(autoDamage <= 0)
			autoDamage = 0;
	}
}
