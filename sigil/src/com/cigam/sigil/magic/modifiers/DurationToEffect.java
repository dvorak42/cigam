package com.cigam.sigil.magic.modifiers;

import com.cigam.sigil.Utils;
import com.cigam.sigil.magic.Spell;
import com.cigam.sigil.magic.SpellDescriptor;

public class DurationToEffect extends Spell{
	private SpellDescriptor toModify;
	
	@Override
	public SpellDescriptor evalEffect() {
		toModify = target.evalEffect();
		toModify.duration*=0.5;
		toModify.effectValue*=2;
		this.gui = Utils.makeRuneGui(Utils.classesToIconPaths.get(this.getClass()));
		return toModify;
	}

	@Override
	public void cast() {
		if(toModify == null)
			evalEffect();
		screen.createSpellEffect(toModify);
	}

}
