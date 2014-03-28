package com.cigam.sigil.materials;

import org.jbox2d.common.Vec2;


import com.cigam.sigil.Constants.Direction;
import com.cigam.sigil.Helper;
import com.cigam.sigil.MaterialDescriptor;
import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.magic.SpellEffect;
import com.cigam.sigil.screens.BattleScreen;

public class Creation extends MaterialDescriptor {
	public Creation() {
		super();
		this.init(null, 0, 0, 0);
	}

	@Override
	public void OnCollide(PhysicalEntity p) {
	}

	@Override
	public void OnCreate(SpellEffect manifestation, BattleScreen b) {
		System.out.println("Creation's onCreate goes off");
		Vec2 castDir = Helper.v2v(Helper.directionToVector(manifestation.direction));
		castDir.normalize();	
		manifestation.target.position = manifestation.body.getPosition().add(castDir.mul(manifestation.body.getFixtureList().m_shape.m_radius));
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
