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


public class Binding extends MaterialDescriptor {
	private PhysicalEntity toBindInto;
	private ArrayList<PhysicalEntity> objectsInRange;
	private MaterialDescriptor attractorType;
	//private ArrayList<MaterialDescriptor> attracteeType;
	private HashMap<MaterialDescriptor, PhysicalEntity> entitiesToBind;
	
	public Binding(SpellDescriptor attractorType, ArrayList<SpellDescriptor> attracteeType) {
		super();
		entitiesToBind = new HashMap<MaterialDescriptor, PhysicalEntity>();
		objectsInRange = new ArrayList<PhysicalEntity>();
		this.attractorType = attractorType.mat;
		for(SpellDescriptor s: attracteeType){
			entitiesToBind.put(s.mat, null);
		}
		this.init(null,0,0,0);
	}

	@Override
	public void OnCollide(PhysicalEntity p) {
		if(!objectsInRange.contains(p)){
			objectsInRange.add(p);
		}
		for(MaterialDescriptor m:entitiesToBind.keySet()){
			if(p.mat.isSameMat(m)&&entitiesToBind.get(m)==null&&(!entitiesToBind.containsKey(p))){
				entitiesToBind.put(m, p);
			}
		}
	}

	@Override
	public void NoCollide(PhysicalEntity p) {
		if(p.equals(toBindInto)){
			for(PhysicalEntity q:toBindInto.boundEntities){
				toBindInto.unbind(q);
			}
		}
		objectsInRange.remove(p);
		for(MaterialDescriptor m:entitiesToBind.keySet()){
			if(entitiesToBind.get(m)==p){
				entitiesToBind.remove(m);
			}
		}
	}
	@Override
	public void Update(){
		if(toBindInto != null){
			for(MaterialDescriptor m:entitiesToBind.keySet()){
				if(entitiesToBind.get(m)!=null){
					toBindInto.bind(entitiesToBind.get(m));
					entitiesToBind.remove(m);
				}
			}
		}
	}
	@Override
	public void OnCreate(SpellEffect manifestation, AdventureScreen createdIn) {
		//System.out.println("spell centered on " + center);
		toBindInto = null;
		//System.out.println("objectsInRange are " + objectsInRange);
		float min = Float.MAX_VALUE;
		for(PhysicalEntity p: objectsInRange){
			float distance = Utils.dist(manifestation, p);
			if(distance < min&&p.body.getType()==BodyType.DynamicBody&&p.body!=manifestation.body&&p.mat.isSameMat(attractorType)){
				min = distance;
				toBindInto = p;
			}
		}
		//System.out.println("toBindInto is " + toBindInto);
	}
	
}
