package com.cigam.sigil.magic.modifiers;

import com.cigam.sigil.Utils;
import com.cigam.sigil.magic.Spell;
import com.cigam.sigil.magic.SpellDescriptor;

public class EffectToArea extends Spell{
	private SpellDescriptor toModify;
	
	@Override
	public SpellDescriptor evalEffect() {
		toModify = target.evalEffect();
		toModify.effectValue*=0.5;
		toModify.shape.setRadius((float) (toModify.shape.getRadius()*(Math.sqrt(2))));
		this.gui = Utils.makeRuneGui(Utils.classesToIconPaths.get(this.getClass()));
		return toModify;
	}

	@Override
	public void cast() {
		screen.createSpellEffect(toModify);
	}

}
