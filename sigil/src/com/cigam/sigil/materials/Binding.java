package com.cigam.sigil.materials;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

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
	private ArrayList<PhysicalEntity> objectsNoCollided;
	private ArrayList<PhysicalEntity> objectsCollided;
	//private ArrayList<MaterialDescriptor> attracteeType;
	private HashMap<MaterialDescriptor, PhysicalEntity> entitiesToBind;
	private float bindingStrength;

	public Binding(SpellDescriptor attractorType, ArrayList<SpellDescriptor> attracteeType, float effectValue) {
		super();
		bindingStrength = effectValue;
		entitiesToBind = new HashMap<MaterialDescriptor, PhysicalEntity>();
		objectsInRange = new ArrayList<PhysicalEntity>();
		objectsNoCollided = new ArrayList<PhysicalEntity>();
		objectsCollided = new ArrayList<PhysicalEntity>();
		this.attractorType = attractorType.mat;
		for(SpellDescriptor s: attracteeType){
			entitiesToBind.put(s.mat, null);
		}
		this.init(null,0,0,0);
	}

	@Override
	public void OnCollide(PhysicalEntity p) {
		if(!objectsCollided.contains(p))
			objectsCollided.add(p);
	}

	@Override
	public void NoCollide(PhysicalEntity p) {
		if(!objectsNoCollided.contains(p))
			objectsNoCollided.add(p);
	}
	@Override
	public void Update(){
		//Add new collisions to objectsInRange
		for(PhysicalEntity p: objectsCollided){
			if(!objectsInRange.contains(p)){
				objectsInRange.add(p);
			}
		}
		//Bind things to be bound
		if(toBindInto != null){
			ArrayList<MaterialDescriptor> keysToDelete = new ArrayList<MaterialDescriptor>();
			for(MaterialDescriptor m:entitiesToBind.keySet()){
				if(entitiesToBind.get(m)!=null){
					toBindInto.bind(entitiesToBind.get(m), bindingStrength);
					keysToDelete.add(m);
				}
			}
			for(MaterialDescriptor m: keysToDelete){
				entitiesToBind.remove(m);
			}
		}
		//Figure out objects to be bound
		for(PhysicalEntity p:objectsCollided){
			Object[] keySet =  entitiesToBind.keySet().toArray();
			for(Object m: keySet){
				if(p.mat.isSameMat((MaterialDescriptor) m)&&entitiesToBind.get(m)==null&&(!entitiesToBind.containsKey(p))){
					entitiesToBind.put((MaterialDescriptor) m, p);
				}
			}
		}
		objectsCollided.clear();
		//Deal with new no-collisions
		for(PhysicalEntity p:objectsNoCollided){
			if(p.equals(toBindInto)){
				Object[] boundEntities = (Object[]) toBindInto.boundEntities.toArray();
				for(Object q: boundEntities){
					toBindInto.unbind((PhysicalEntity) q, bindingStrength);
				}
				Object[] imbuedEntities = (Object[]) toBindInto.imbuedEntities.toArray();
				for(Object q: imbuedEntities){
					toBindInto.unbind((PhysicalEntity) q, bindingStrength);
				}
			}
			objectsInRange.remove(p);
			MaterialDescriptor toRemove = null;
			for(MaterialDescriptor m:entitiesToBind.keySet()){
				if(entitiesToBind.get(m)==p){
					toRemove = m;
				}
			}
			if(toRemove != null){
				entitiesToBind.remove(toRemove);
			}
		}
		objectsNoCollided.clear();
	}
	@Override
	public void OnCreate(SpellEffect manifestation, AdventureScreen createdIn) {
		//System.out.println("spell centered on " + center);
		toBindInto = null;
		//System.out.println("objectsInRange are " + objectsInRange);
		float min = Float.MAX_VALUE;
		this.Update();
		//System.out.println(objectsInRange);
		for(PhysicalEntity p: objectsInRange){
			float distance = Utils.dist(manifestation, p);
			if(distance < min&&p.body.getType()==BodyType.DynamicBody&&p.body!=manifestation.body&&p.mat.isSameMat(attractorType)){
				min = distance;
				toBindInto = p;
			}
		}
		//System.out.println("toBindInto is " + toBindInto);
	}

	@Override
	public void onDestroy(AdventureScreen destroyedIn){
		toBindInto = null;
		entitiesToBind.clear();
		objectsInRange.clear();
	}
}
