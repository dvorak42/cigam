package com.cigam.sigil.magic.targets;

import com.cigam.sigil.Utils;
import com.cigam.sigil.magic.Spell;
import com.cigam.sigil.magic.SpellDescriptor;
import com.cigam.sigil.materials.Fire;

public class FireRune extends Spell {
	public SpellDescriptor material;
	
	public FireRune(){
		this.material = new SpellDescriptor(new Fire());
		this.gui = Utils.makeRuneGui(Utils.classesToIconPaths.get(this.getClass()));
	}

	@Override
	public SpellDescriptor evalEffect() {
		return new SpellDescriptor(new Fire());
	}

	@Override
	public void cast() {}

}
