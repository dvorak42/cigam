package com.cigam.sigil.magic;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.cigam.sigil.Constants;
import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.materials.Backgroundium;
import com.cigam.sigil.materials.Fire;
import com.cigam.sigil.screens.AdventureScreen;

public abstract class MaterialDescriptor {
	public ParticleEffect image;
	public float manaDensityFactor;
	public float manaCapacityFactor;
	//TODO: animated base stats
	//TODO: animated base personality
	//TODO: return value
	//TODO: material composition
	//TODO: material cohesion
	public float hardness;
	public float density;

	
	public MaterialDescriptor(){
	}
	public void init(ParticleEffect img, float md, float mc, float h){
			this.image = img;
			this.manaDensityFactor = md;
			this.manaCapacityFactor = mc;
			this.hardness = h;	
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
	public void onDestroy(AdventureScreen destroyedIn){};
	public boolean isSameMat(MaterialDescriptor m){
		return m.getClass().equals(this.getClass());
	};
	public void scaleManifestation(float x, float y){};
}
