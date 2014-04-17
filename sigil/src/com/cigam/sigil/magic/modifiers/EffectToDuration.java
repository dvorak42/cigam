package com.cigam.sigil.magic.modifiers;

import com.cigam.sigil.Utils;
import com.cigam.sigil.magic.Spell;
import com.cigam.sigil.magic.SpellDescriptor;

public class EffectToDuration extends Spell{
	private SpellDescriptor toModify;
	
	@Override
	public SpellDescriptor evalEffect() {
		toModify = target.evalEffect();
		toModify.duration*=2;
		toModify.effectValue*=0.5;
		this.gui = Utils.makeRuneGui(Utils.classesToIconPaths.get(this.getClass()));
		return toModify;
	}

	@Override
	public void cast() {
		screen.createSpellEffect(toModify);
	}

}
