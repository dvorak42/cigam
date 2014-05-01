package com.cigam.sigil.magic.modifiers;

import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.Utils;
import com.cigam.sigil.magic.Spell;
import com.cigam.sigil.magic.SpellDescriptor;
import com.cigam.sigil.screens.AdventureScreen;

public class EffectToArea extends Spell{
	private SpellDescriptor toModify;
	
	@Override
	public SpellDescriptor evalEffect(PhysicalEntity caster) {
		toModify = target.evalEffect(caster);
		toModify.effectValue*=0.5;
		toModify.shape.setRadius((float) (toModify.shape.getRadius()*(Math.sqrt(2))));
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
