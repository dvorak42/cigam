package com.cigam.sigil.magic.verbs;

import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.Player;
import com.cigam.sigil.magic.Spell;
import com.cigam.sigil.magic.SpellDescriptor;
import com.cigam.sigil.screens.AdventureScreen;


public class TopLevelSpell extends Spell {
	private SpellDescriptor effect;
	
	public TopLevelSpell(Player c,  AdventureScreen b) {
		super();
		this.screen = b;
		this.caster = c;
		argsNum = 0;
		this.validArguments.remove(Type.TARGET);
		this.validTargets.remove(Type.TARGET);
	}

	@Override
	public SpellDescriptor evalEffect() {
		effect = target.evalEffect();
		return effect;
	}
	@Override
	public void cast() {
		if(target != null)
			target.cast();
	}
}
