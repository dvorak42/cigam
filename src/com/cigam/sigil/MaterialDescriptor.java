package com.cigam.sigil;

import java.util.HashMap;

import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.newdawn.slick.Image;

import com.cigam.sigil.magic.Area;
import com.cigam.sigil.magic.Die;
import com.cigam.sigil.magic.Area.baseArea;
import com.cigam.sigil.magic.SpellEffect;
import com.cigam.sigil.materials.*;
import com.cigam.sigil.screens.BattleScreen;

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
	public float density;
	public Die effectFactor;
	
	public MaterialDescriptor(){
	}
	public void init(Image img, float md, float mc, float h){
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
	public void OnCreate(SpellEffect manifestation, BattleScreen createdIn){};

}
