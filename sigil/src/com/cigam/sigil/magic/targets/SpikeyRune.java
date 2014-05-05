package com.cigam.sigil.magic.targets;

import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.Utils;
import com.cigam.sigil.magic.Spell;
import com.cigam.sigil.magic.SpellDescriptor;
import com.cigam.sigil.materials.SpikeyMat;
import com.cigam.sigil.materials.StickyMat;
import com.cigam.sigil.screens.AdventureScreen;

public class SpikeyRune extends Spell {
	public SpellDescriptor material;
	
	public SpikeyRune(){
		this.material = new SpellDescriptor(new SpikeyMat());
		this.gui = Utils.makeRuneGui(Utils.classesToIconPaths.get(this.getClass()));
		type = Spell.Type.TARGET;
	}

	@Override
	public SpellDescriptor evalEffect(PhysicalEntity caster) {
		return new SpellDescriptor(new StickyMat());
	}

	@Override
	public void cast(AdventureScreen screen, PhysicalEntity caster) {}

}