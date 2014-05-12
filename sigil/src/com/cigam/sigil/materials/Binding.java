package com.cigam.sigil.materials;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
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
	private SpellEffect manifestation;

	
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
		ParticleEffect p = new ParticleEffect();
		p.load(Gdx.files.internal("particleFiles/bind/bindSpell"), Gdx.files.internal("art/particles"));
		this.init(p,0,0,0);
	}

	@Override
	public void OnCollide(PhysicalEntity p) {
		if(!objectsCollided.contains(p)&&p.active)
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
		doCollisions();
		//Bind things to be bound
		doBinding();
		//Figure out objects to be bound
		findThingsToBind();
		//System.out.println(entitiesToBind);
		//Deal with new no-collisions
		doUncollide();
		//remove nonactive objects
		for(int i = 0; i < objectsInRange.size();i ++){
			if(!objectsInRange.get(i).active()||!objectsInRange.get(i).visible()){
				objectsInRange.remove(objectsInRange.get(i));
			}
		}
	}
	@Override
	public void OnCreate(SpellEffect manifestation, AdventureScreen createdIn) {
		toBindInto = null;
		//float min = Float.MAX_VALUE;
		this.manifestation = manifestation;
		this.Update();
		//System.out.println(objectsInRange);
		/*for(PhysicalEntity p: objectsInRange){
			if(p == null || !p.active() || p.body == null)
				continue;
			float distance = Utils.dist(manifestation, p);
			if(distance < min&&p.body.getType()==BodyType.DynamicBody&&p.body!=manifestation.body&&p.mat.isSameMat(attractorType)){
				min = distance;
				toBindInto = p;
			}
		}*/
		//System.out.println("toBindInto is " + toBindInto);
	}

	@Override
	public void onDestroy(AdventureScreen destroyedIn){
		toBindInto = null;
		entitiesToBind.clear();
		objectsInRange.clear();
	}
	private void doCollisions(){
		for(PhysicalEntity p: objectsCollided){
			if(!objectsInRange.contains(p)){
				objectsInRange.add(p);
			}
		}
		objectsCollided.clear();
	}
	private void doBinding(){
		if(toBindInto != null&&toBindInto.active){
			ArrayList<MaterialDescriptor> boundTypes = new ArrayList<MaterialDescriptor>();
			for(MaterialDescriptor m:entitiesToBind.keySet()){
				if(entitiesToBind.get(m)!=null&&entitiesToBind.get(m).active&&entitiesToBind.get(m).visible){
					//System.out.println("Binding is binding " + entitiesToBind.get(m) + " into " + toBindInto);
					toBindInto.bind(entitiesToBind.get(m), bindingStrength);
					objectsInRange.remove(entitiesToBind.get(m));
					entitiesToBind.put(m, null);
					boundTypes.add(m);
					//keysToDelete.add(m);
				}
			}
			for(MaterialDescriptor m:boundTypes){
				entitiesToBind.keySet().remove(m);
			}
			boundTypes.clear();
		} else { //Look for new toBindInto if necessary
			findNewToBindInto();
		}
	}
	private void findNewToBindInto(){
		float min = Float.MAX_VALUE;
		for(PhysicalEntity p: objectsInRange){
			if(p == null || !p.active() || p.body == null || p.mat == null){
				continue;
			} else {
				float distance = Utils.dist(manifestation, p);
				if(distance < min&&p.body.getType()==BodyType.DynamicBody&&p.body!=manifestation.body&&p.mat.isSameMat(attractorType)){
					min = distance;
					toBindInto = p;
				}
			}
		}
		//System.out.println("toBindInto is " + toBindInto);
	}
	private void findThingsToBind(){
		for(PhysicalEntity p:objectsInRange){
			Object[] keySet =  entitiesToBind.keySet().toArray();
			for(Object m: keySet){
				//System.out.println(p + " active state is " + p.active);
				if(p == null || !p.active() || p.body == null || p.mat == null){
					continue;
				}else{
					if(p.mat.isSameMat((MaterialDescriptor) m)&&entitiesToBind.get(m)==null&&(!entitiesToBind.containsKey(p)&&!p.equals(toBindInto)&&p.active)){
						entitiesToBind.put((MaterialDescriptor) m, p);
					}
				}
			}
		}
	}
	private void doUncollide(){
		for(PhysicalEntity p:objectsNoCollided){
			if(p.equals(toBindInto)){
				Object[] boundEntities = (Object[]) toBindInto.boundEntities.toArray();
				for(Object q: boundEntities){
					toBindInto.unbind((PhysicalEntity) q, bindingStrength);
					entitiesToBind.put(((PhysicalEntity) q).mat, null);

				}
				Object[] imbuedEntities = (Object[]) toBindInto.imbuedEntities.toArray();
				for(Object q: imbuedEntities){
					toBindInto.unbind((PhysicalEntity) q, bindingStrength);
				}
				toBindInto = null;
				objectsInRange.clear();
				for(MaterialDescriptor m:entitiesToBind.keySet()){
					entitiesToBind.put(m, null);
				}
			}
			objectsInRange.remove(p);
			for(MaterialDescriptor m:entitiesToBind.keySet()){
				if(entitiesToBind.get(m)==p){
					entitiesToBind.put(m, null);
				}
			}
		}
		objectsNoCollided.clear();
	}
}
