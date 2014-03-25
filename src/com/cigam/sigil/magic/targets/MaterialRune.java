package com.cigam.sigil.magic.targets;

import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.magic.MaterialDescriptor;
import com.cigam.sigil.magic.Target;

public class MaterialRune extends Target {
	public MaterialDescriptor material;
	
	public MaterialRune(MaterialDescriptor mat){
		this.material = mat;
	}
	
	@Override
	public void topEvalEffect() {
	}

	@Override
	public MaterialDescriptor evalEffect() {
		return this.material;
	}

}
