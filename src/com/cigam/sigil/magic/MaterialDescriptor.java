package com.cigam.sigil.magic;

import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.newdawn.slick.Image;

import com.cigam.sigil.PhysicalEntity;

public abstract class MaterialDescriptor {
	public Image image;
	public float manaDensityFactor;
	public float manaCapacityFactor;
	//TODO: animated base stats
	//TODO: animated base personality
	//TODO: return value
	//TODO: material composition
	//TODO: material cohesion
	public float hardness;
	public Area defaultArea;
	public BodyDef bd;
	public FixtureDef fd;
	public Die effectFactor;
	public float duration;
	
	public MaterialDescriptor(){
	}
	public void init(Image img, float md, float mc, float h, float dur, Area.baseArea shape, FixtureDef fd, BodyDef bd){
			this.image = img;
			this.manaDensityFactor = md;
			this.manaCapacityFactor = mc;
			this.hardness = h;
			this.defaultArea = new Area(shape, 1);
			this.bd = bd;
			this.fd = fd;
			this.duration = dur;	
			this.effectFactor = new Die(1, 1);
	}
	public abstract void OnCollide(PhysicalEntity p);
	public abstract void NoCollide(PhysicalEntity b);
	public abstract void Update();
	public abstract void OnCreate(PhysicalEntity manifestation);

}
