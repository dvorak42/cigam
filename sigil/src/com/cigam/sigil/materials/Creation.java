package com.cigam.sigil.materials;

import com.badlogic.gdx.math.Vector2;
import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.Utils;
import com.cigam.sigil.magic.MaterialDescriptor;
import com.cigam.sigil.magic.SpellEffect;
import com.cigam.sigil.screens.AdventureScreen;

public class Creation extends MaterialDescriptor {
	public Creation() {
		super();
		this.init(null, 0, 0, 0);
	}

	@Override
	public void OnCollide(PhysicalEntity p) {
	}

	@Override
	public void OnCreate(SpellEffect manifestation, AdventureScreen b) {
		System.out.println("Creation's onCreate goes off");
		Vector2 castDir = Utils.angleToVector(manifestation.angle);
		castDir.nor();	
		//TODO: Use correct offset.
		manifestation.target.position = manifestation.body.getPosition().cpy().add(castDir.cpy().scl(10));
		manifestation.target.duration = manifestation.target.duration*manifestation.duration;
		//System.out.println(target.bd.position + " is caster location");
		//System.out.println(bd.position.add(castDir.mul(this.fd.shape.m_radius)) + " is created object location");
		b.createSpellEffect(manifestation.target);
	}

	@Override
	public void NoCollide(PhysicalEntity b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Update() {
		// TODO Auto-generated method stub
		
	}

}
