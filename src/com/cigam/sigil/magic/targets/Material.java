package com.cigam.sigil.magic.targets;

import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.magic.MaterialDescriptor;
import com.cigam.sigil.magic.Target;

public class Material extends Target {
	public MaterialDescriptor material;
	
	public Material(MaterialDescriptor mat){
		this.material = mat;
		this.duration = 1;
	}
	
	@Override
	public void topEvalEffect() {
	}

	@Override
	public MaterialDescriptor evalEffect() {
		return this.material;
	}

}
