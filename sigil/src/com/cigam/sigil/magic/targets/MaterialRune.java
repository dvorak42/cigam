package com.cigam.sigil.magic.targets;

import com.cigam.sigil.magic.SpellDescriptor;
import com.cigam.sigil.magic.Target;

public class MaterialRune extends Target {
	public SpellDescriptor material;
	
	public MaterialRune(SpellDescriptor mat){
		this.material = mat;
	}
	
	@Override
	public void topEvalEffect() {
	}

	@Override
	public SpellDescriptor evalEffect() {
		return material;
	}

}
