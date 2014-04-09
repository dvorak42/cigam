package com.cigam.sigil.magic.targets;

import com.cigam.sigil.magic.MaterialDescriptor;
import com.cigam.sigil.magic.Spell;
import com.cigam.sigil.magic.SpellDescriptor;

public class MaterialRune extends Spell {
	public SpellDescriptor material;
	
	public MaterialRune(MaterialDescriptor mat){
		this.material = new SpellDescriptor(mat);
	}

	@Override
	public SpellDescriptor evalEffect() {
		return material;
	}

	@Override
	public void cast() {}

}
