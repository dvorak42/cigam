package com.cigam.sigil.magic.modifiers;

import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.Utils;
import com.cigam.sigil.magic.Spell;
import com.cigam.sigil.magic.SpellDescriptor;
import com.cigam.sigil.screens.AdventureScreen;

public class EffectToDuration extends Spell{
	private SpellDescriptor toModify;
	
	@Override
	public SpellDescriptor evalEffect(PhysicalEntity caster) {
		toModify = target.evalEffect(caster);
		toModify.duration*=2;
		toModify.effectValue*=0.5;
		this.gui = Utils.makeRuneGui(Utils.classesToIconPaths.get(this.getClass()));
		return toModify;
	}

	@Override
	public void cast(AdventureScreen screen, PhysicalEntity caster) {
		if(toModify == null)
			evalEffect(caster);
		screen.createSpellEffect(toModify);
	}

}
