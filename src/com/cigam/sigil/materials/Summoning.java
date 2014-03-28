package com.cigam.sigil.materials;

import java.util.ArrayList;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

import com.cigam.sigil.MaterialDescriptor;
import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.magic.SpellEffect;
import com.cigam.sigil.screens.BattleScreen;


public class Summoning extends MaterialDescriptor {
	private PhysicalEntity attractor;
	private ArrayList<PhysicalEntity> objectsInRange;
	public Summoning() {
		super();
		objectsInRange = new ArrayList<PhysicalEntity>();
		this.init(null,0,0,0);

	}

	@Override
	public void OnCollide(PhysicalEntity p) {
		System.out.println("collided with " + p);
		objectsInRange.add(p);
	}

	@Override
	public void NoCollide(PhysicalEntity p) {
		System.out.println("not colliding with " + p);
		objectsInRange.remove(p);
	}
	@Override
	public void Update(){
		if(attractor != null){
			for(PhysicalEntity p: objectsInRange){
				Vec2 dir = attractor.body.getPosition().sub(p.body.getPosition());
				dir.normalize();
				p.body.applyForceToCenter(dir.mul(-10000));
			}
		}
	}
	@Override
	public void OnCreate(SpellEffect manifestation, BattleScreen createdIn) {
		Vec2 center = manifestation.body.getPosition();
		System.out.println("spell centered on " + center);
		attractor = null;
		System.out.println("objectsInRange are " + objectsInRange);
		float min = Float.MAX_VALUE;
		for(PhysicalEntity p: objectsInRange){
			float distance = manifestation.body.getPosition().sub(p.body.getPosition()).length();
			if(distance < min&&p.body.getType()==BodyType.DYNAMIC&&p.body!=manifestation.body){
				min = distance;
				attractor = p;
			}
		}
		System.out.println("Attractor is " + attractor);
	}

}
