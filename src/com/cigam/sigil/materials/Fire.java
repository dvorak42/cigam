package com.cigam.sigil.materials;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import com.cigam.sigil.MaterialDescriptor;
import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.magic.Area;

public class Fire extends MaterialDescriptor {

	public Fire() {
		super();
		this.init(null,0,0,0);
	}

	@Override
	public void OnCollide(PhysicalEntity p) {
		// TODO Does damage, sets things on fire, etc...
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
