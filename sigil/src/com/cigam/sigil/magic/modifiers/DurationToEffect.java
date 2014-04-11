package com.cigam.sigil.magic.modifiers;

import com.cigam.sigil.magic.Spell;
import com.cigam.sigil.magic.SpellDescriptor;

public class DurationToEffect extends Spell{
	private SpellDescriptor toModify;
	
	@Override
	public SpellDescriptor evalEffect() {
		toModify = target.evalEffect();
		toModify.duration*=0.5;
		toModify.effectValue*=2;
		return toModify;
	}

	@Override
	public void cast() {
		screen.createSpellEffect(toModify);
	}

}
