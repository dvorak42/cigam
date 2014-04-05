package com.cigam.sigil.magic;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.materials.*;
import com.cigam.sigil.screens.AdventureScreen;

public abstract class MaterialDescriptor {
	public Sprite image;
	public float manaDensityFactor;
	public float manaCapacityFactor;
	//TODO: animated base stats
	//TODO: animated base personality
	//TODO: return value
	//TODO: material composition
	//TODO: material cohesion
	public float hardness;
	public float density;
	public Die effectFactor;
	
	public MaterialDescriptor(){
	}
	public void init(Sprite img, float md, float mc, float h){
			this.image = img;
			this.manaDensityFactor = md;
			this.manaCapacityFactor = mc;
			this.hardness = h;	
			this.effectFactor = new Die(1, 1);
	}
	public static final HashMap<String, MaterialDescriptor> strToMats = new HashMap<String, MaterialDescriptor>();
	static {
		strToMats.put("fire", new Fire());
		strToMats.put("backgroundium", new Backgroundium());
	}
	public void OnCollide(PhysicalEntity p){};
	public void NoCollide(PhysicalEntity b){};
	public void Update(){};
	public void OnCreate(SpellEffect manifestation, AdventureScreen createdIn){};
	public boolean isSameMat(MaterialDescriptor m){
		return m.getClass().equals(this.getClass());
	};

}
