package com.cigam.sigil.magic.targets;

import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.Utils;
import com.cigam.sigil.magic.Spell;
import com.cigam.sigil.magic.SpellDescriptor;
import com.cigam.sigil.materials.Fire;
import com.cigam.sigil.screens.AdventureScreen;

public class FireRune extends Spell {
	public SpellDescriptor material;
	
	public FireRune(){
		this.material = new SpellDescriptor(new Fire());
		this.gui = Utils.makeRuneGui(Utils.classesToIconPaths.get(this.getClass()));
		type = Spell.Type.TARGET;
	}

	@Override
	public SpellDescriptor evalEffect(PhysicalEntity caster) {
		return new SpellDescriptor(new Fire());
	}

	@Override
	public void cast(AdventureScreen screen, PhysicalEntity caster) {}

}
