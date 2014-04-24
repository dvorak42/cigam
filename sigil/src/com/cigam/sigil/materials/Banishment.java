package com.cigam.sigil.materials;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
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
	private HashMap<MaterialDescriptor, PhysicalEntity> entitiesToPush;
	private SpellEffect manifestation;
	
	public Banishment(SpellDescriptor attractorType, ArrayList<SpellDescriptor> attracteeType, float force) {
		super();
		this.force = force;
		entitiesToPush = new HashMap<MaterialDescriptor, PhysicalEntity>();
		objectsInRange = new ArrayList<PhysicalEntity>();
		//this.attracteeType = new ArrayList<MaterialDescriptor>();
		this.attractorType = attractorType.mat;
		for(SpellDescriptor s: attracteeType){
			//this.attracteeType.add(s.mat);
			entitiesToPush.put(s.mat, null);
		}
		ParticleEffect p = new ParticleEffect();
		p.load(Gdx.files.internal("art/particles/banish.p"), Gdx.files.internal("art/particles"));
		this.init(p,0,0,0);
	}

	@Override
	public void OnCollide(PhysicalEntity p) {
		if(!objectsInRange.contains(p)){
			objectsInRange.add(p);
		}
		//System.out.println("collided with " + p);
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
		if(p == attractor){
			attractor = null;
		}
	}
	@Override
	public void Update(){
		if(attractor != null){
			if(!attractor.active()){
				attractor = null;
			}
			for(MaterialDescriptor m:entitiesToPush.keySet()){
				if(entitiesToPush.get(m)!=null){
					if(entitiesToPush.get(m).active()){
						Vector2 dir = attractor.body.getWorldCenter().sub(entitiesToPush.get(m).body.getWorldCenter());
						dir.nor();
						entitiesToPush.get(m).body.applyForceToCenter(dir.scl(force), true);
					}
					else {
						entitiesToPush.put(m, null);
						getEntityToPush(m);
					}
				}
			}
			if(this.image!=null){
				//this.image.setPosition(attractor.getPosition().x, attractor.getPosition().y);
			}
		} else {
			OnCreate(manifestation, null);
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
		attractor = null;
		this.manifestation = manifestation;
		//System.out.println("objectsInRange are " + objectsInRange);
		float min = Float.MAX_VALUE;
		for(PhysicalEntity p: objectsInRange){
			if(p == null || !p.active() || p.body == null)
				continue;

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
