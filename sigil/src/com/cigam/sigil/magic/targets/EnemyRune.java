package com.cigam.sigil.magic.targets;

import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.Utils;
import com.cigam.sigil.magic.Spell;
import com.cigam.sigil.magic.SpellDescriptor;
import com.cigam.sigil.materials.EnemyMat;
import com.cigam.sigil.materials.StickyMat;
import com.cigam.sigil.screens.AdventureScreen;

public class EnemyRune extends Spell {
	public SpellDescriptor material;
	
	public EnemyRune(){
		this.material = new SpellDescriptor(new EnemyMat());
		this.gui = Utils.makeRuneGui(Utils.classesToIconPaths.get(this.getClass()));
		type = Spell.Type.TARGET;
	}

	@Override
	public SpellDescriptor evalEffect(PhysicalEntity caster) {
		return new SpellDescriptor(new EnemyMat());
	}

	@Override
	public void cast(AdventureScreen screen, PhysicalEntity caster) {}

}
