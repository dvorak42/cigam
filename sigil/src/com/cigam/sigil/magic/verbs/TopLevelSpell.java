package com.cigam.sigil.magic.verbs;

import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.magic.Spell;
import com.cigam.sigil.magic.SpellDescriptor;
import com.cigam.sigil.screens.AdventureScreen;


public class TopLevelSpell extends Spell {
	private SpellDescriptor effect;
	
	public TopLevelSpell() {
		super();
		argsNum = 0;
		this.validArguments.remove(Type.TARGET);
		this.validTargets.remove(Type.TARGET);
	}

	@Override
	public SpellDescriptor evalEffect(PhysicalEntity caster) {
		effect = target.evalEffect(caster);
		return effect;
	}
	@Override
	public void cast(AdventureScreen screen, PhysicalEntity caster) {
		if(target != null)
			target.cast(screen, caster);
	}
}
