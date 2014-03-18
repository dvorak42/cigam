package com.cigam.sigil.magic;

import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.newdawn.slick.Image;

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
	
	public MaterialDescriptor(){
	}
	public void init(Image img, float md, float mc, float h, Area.baseArea shape, FixtureDef fd, BodyDef bd){
			this.image = img;
			this.manaDensityFactor = md;
			this.manaCapacityFactor = mc;
			this.hardness = h;
			this.defaultArea = new Area(shape, 1);
			this.bd = bd;
			this.fd = fd;
			this.effectFactor = new Die(1, 1);
	}
	public abstract void OnCollide(MaterialDescriptor m);

}
