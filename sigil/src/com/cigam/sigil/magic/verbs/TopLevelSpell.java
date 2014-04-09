package com.cigam.sigil.magic.verbs;

import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.magic.*;
import com.cigam.sigil.screens.*;


public class TopLevelSpell extends Spell {
	private SpellDescriptor effect;
	
	public TopLevelSpell(PhysicalEntity c,  AdventureScreen b) {
		super();
		this.screen = b;
		this.caster = c;
		argsNum = 0;
	}

	@Override
	public SpellDescriptor evalEffect() {
		effect = target.evalEffect();
		return effect;
	}
	@Override
	public void cast() {
		target.cast();
	}
}
