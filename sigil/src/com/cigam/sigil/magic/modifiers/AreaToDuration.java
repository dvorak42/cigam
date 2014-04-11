package com.cigam.sigil.magic.modifiers;

import com.cigam.sigil.magic.Spell;
import com.cigam.sigil.magic.SpellDescriptor;

public class AreaToDuration extends Spell{
	private SpellDescriptor toModify;
	
	@Override
	public SpellDescriptor evalEffect() {
		toModify = target.evalEffect();
		toModify.duration*=2;
		toModify.shape.setRadius((float) (toModify.shape.getRadius()/(Math.sqrt(2))));
		return toModify;
	}

	@Override
	public void cast() {
		screen.createSpellEffect(toModify);
	}

}
