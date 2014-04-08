package com.cigam.sigil.materials;

import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.magic.MaterialDescriptor;

public class Fire extends MaterialDescriptor {

	public Fire() {
		super();
		this.init(null,3,0.2f,0);
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
