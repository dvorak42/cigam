package com.cigam.sigil.materials;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.Utils;
import com.cigam.sigil.magic.MaterialDescriptor;
import com.cigam.sigil.magic.SpellDescriptor;
import com.cigam.sigil.magic.SpellEffect;
import com.cigam.sigil.screens.AdventureScreen;


public class Imbueing extends MaterialDescriptor {
	private PhysicalEntity toImbueInto;
	private ArrayList<PhysicalEntity> objectsInRange;
	private MaterialDescriptor attractorType;
	private HashMap<MaterialDescriptor, PhysicalEntity> entitiesToImbue;
	
	public Imbueing(SpellDescriptor attractorType, ArrayList<SpellDescriptor> attracteeType) {
		super();
		entitiesToImbue = new HashMap<MaterialDescriptor, PhysicalEntity>();
		objectsInRange = new ArrayList<PhysicalEntity>();
		this.attractorType = attractorType.mat;
		for(SpellDescriptor s: attracteeType){
			entitiesToImbue.put(s.mat, null);
		}
		this.init(null,0,0,0);
	}

	@Override
	public void OnCollide(PhysicalEntity p) {
		if(!objectsInRange.contains(p)){
			objectsInRange.add(p);
		}
		for(MaterialDescriptor m:entitiesToImbue.keySet()){
			if(p.mat.isSameMat(m)&&entitiesToImbue.get(m)==null&&(!entitiesToImbue.containsKey(p))){
				entitiesToImbue.put(m, p);
			}
		}
	}

	@Override
	public void NoCollide(PhysicalEntity p) {
		if(p.equals(toImbueInto)){
			for(PhysicalEntity q:toImbueInto.boundEntities){
				toImbueInto.unbind(q);
			}
		}
		objectsInRange.remove(p);
		for(MaterialDescriptor m:entitiesToImbue.keySet()){
			if(entitiesToImbue.get(m)==p){
				entitiesToImbue.remove(m);
			}
		}
	}
	@Override
	public void Update(){
		if(toImbueInto != null){
			for(MaterialDescriptor m:entitiesToImbue.keySet()){
				if(entitiesToImbue.get(m)!=null){
					toImbueInto.imbue(entitiesToImbue.get(m));
					entitiesToImbue.remove(m);
				}
			}
		}
	}
	@Override
	public void OnCreate(SpellEffect manifestation, AdventureScreen createdIn) {
		//System.out.println("spell centered on " + center);
		toImbueInto = null;
		//System.out.println("objectsInRange are " + objectsInRange);
		float min = Float.MAX_VALUE;
		for(PhysicalEntity p: objectsInRange){
			float distance = Utils.dist(manifestation, p);
			if(distance < min&&p.body.getType()==BodyType.DynamicBody&&p.body!=manifestation.body&&p.mat.isSameMat(attractorType)){
				min = distance;
				toImbueInto = p;
			}
		}
		//System.out.println("toBindInto is " + toBindInto);
	}
	
}
