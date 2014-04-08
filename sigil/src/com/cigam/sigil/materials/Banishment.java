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


public class Banishment extends MaterialDescriptor {
	private PhysicalEntity attractor;
	private ArrayList<PhysicalEntity> objectsInRange;
	private MaterialDescriptor attractorType;
	private float force;
	//private ArrayList<MaterialDescriptor> attracteeType;
	private HashMap<MaterialDescriptor, PhysicalEntity> entitiesToPush;
	
	public Banishment(SpellDescriptor attractorType, ArrayList<SpellDescriptor> attracteeType, float f) {
		super();
		force = f;
		entitiesToPush = new HashMap<MaterialDescriptor, PhysicalEntity>();
		objectsInRange = new ArrayList<PhysicalEntity>();
		//this.attracteeType = new ArrayList<MaterialDescriptor>();
		this.attractorType = attractorType.mat;
		for(SpellDescriptor s: attracteeType){
			//this.attracteeType.add(s.mat);
			entitiesToPush.put(s.mat, null);
		}
		this.init(null,0,0,0);
	}

	@Override
	public void OnCollide(PhysicalEntity p) {
		if(!objectsInRange.contains(p)){
			objectsInRange.add(p);
		}
		//System.out.println("collided with " + p);
		objectsInRange.add(p);
		for(MaterialDescriptor m:entitiesToPush.keySet()){
			if(p.mat.isSameMat(m)&&entitiesToPush.get(m)==null&&(!entitiesToPush.containsKey(p))){
				entitiesToPush.put(m, p);
			}
		}
	}

	@Override
	public void NoCollide(PhysicalEntity p) {
		//System.out.println("not colliding with " + p);
		objectsInRange.remove(p);
		for(MaterialDescriptor m:entitiesToPush.keySet()){
			if(entitiesToPush.get(m)==p){
				entitiesToPush.put(m, null);
				getEntityToPush(m);
			}
		}
	}
	@Override
	public void Update(){
		if(attractor != null){
			for(MaterialDescriptor m:entitiesToPush.keySet()){
				if(entitiesToPush.get(m)!=null){
					Vector2 dir = attractor.body.getPosition().sub(entitiesToPush.get(m).body.getPosition());
					dir.nor();
					entitiesToPush.get(m).body.applyForceToCenter(dir.scl(force), true);
				}
			}
		}
	}
	private void getEntityToPush(MaterialDescriptor m){
		for(PhysicalEntity p: objectsInRange){
			if(p.mat.isSameMat((m))&&(!entitiesToPush.containsValue(p))&&(entitiesToPush.get(m)==null||Utils.dist(entitiesToPush.get(m),attractor)<Utils.dist(p,attractor))){
				entitiesToPush.put(m, p);
			}
		}
	}
	@Override
	public void OnCreate(SpellEffect manifestation, AdventureScreen createdIn) {
		//System.out.println("spell centered on " + center);
		attractor = null;
		//System.out.println("objectsInRange are " + objectsInRange);
		float min = Float.MAX_VALUE;
		for(PhysicalEntity p: objectsInRange){
			float distance = Utils.dist(manifestation, p);
			if(distance < min&&p.body.getType()==BodyType.DynamicBody&&p.body!=manifestation.body&&p.mat.isSameMat(attractorType)){
				min = distance;
				attractor = p;
			}
		}
		//System.out.println("Attractor is " + attractor);
	}
	@Override
	public void onDestroy(AdventureScreen destroyedIn){
		attractor = null;
		entitiesToPush.clear();
		objectsInRange.clear();
	}

}
