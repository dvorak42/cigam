package com.cigam.sigil.materials;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.magic.MaterialDescriptor;
import com.cigam.sigil.magic.SpellDescriptor;
import com.cigam.sigil.magic.SpellEffect;
import com.cigam.sigil.screens.AdventureScreen;


public class Binding extends MaterialDescriptor {
	private PhysicalEntity attractor;
	private ArrayList<PhysicalEntity> objectsInRange;
	private MaterialDescriptor attractorType;
	private ArrayList<MaterialDescriptor> attracteeType;
	//TODO: This
	public Binding(SpellDescriptor attractorType, ArrayList<SpellDescriptor> attracteeType) {
		super();
		this.objectsInRange = new ArrayList<PhysicalEntity>();
		this.attracteeType = new ArrayList<MaterialDescriptor>();
		this.attractorType = attractorType.mat;
		for(SpellDescriptor s: attracteeType){
			this.attracteeType.add(s.mat);
		}
		this.init(null,0,0,0);
	}

	@Override
	public void OnCollide(PhysicalEntity p) {
		//System.out.println("collided with " + p);
		objectsInRange.add(p);
	}

	@Override
	public void NoCollide(PhysicalEntity p) {
		//System.out.println("not colliding with " + p);
		objectsInRange.remove(p);
	}
	@Override
	public void Update(){
		if(attractor != null){
			for(PhysicalEntity p: objectsInRange){
				if(p.mat.isSameMat((attracteeType.get(0)))){
					Vector2 dir = attractor.body.getPosition().sub(p.body.getPosition());
					dir.nor();
					p.body.applyForceToCenter(dir.scl(10000), true);
				}
			}
		}
	}
	@Override
	public void OnCreate(SpellEffect manifestation, AdventureScreen createdIn) {
		Vector2 center = manifestation.body.getPosition();
		System.out.println("spell centered on " + center);
		attractor = null;
		System.out.println("objectsInRange are " + objectsInRange);
		float min = Float.MAX_VALUE;
		for(PhysicalEntity p: objectsInRange){
			float distance = manifestation.body.getPosition().cpy().sub(p.body.getPosition()).len();
			if(distance < min&&p.body.getType()==BodyType.DynamicBody&&p.body!=manifestation.body&&p.mat.isSameMat(attractorType)){
				min = distance;
				attractor = p;
			}
		}
		System.out.println("Attractor is " + attractor);
	}

}
