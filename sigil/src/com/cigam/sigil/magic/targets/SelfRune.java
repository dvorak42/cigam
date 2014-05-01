package com.cigam.sigil.magic.targets;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.Utils;
import com.cigam.sigil.magic.Spell;
import com.cigam.sigil.magic.SpellDescriptor;
import com.cigam.sigil.materials.SelfMat;
import com.cigam.sigil.screens.AdventureScreen;

public class SelfRune extends Spell {
	private SpellDescriptor self;
	public SelfRune(){
		CircleShape c = new CircleShape();
		c.setRadius(10);
		self = new SpellDescriptor(new SelfMat(), 10, 1,0, null, null, 0, c, Vector2.Zero);
		this.gui = Utils.makeRuneGui(Utils.classesToIconPaths.get(this.getClass()));
		type = Spell.Type.TARGET;
	}
	


	@Override
	public SpellDescriptor evalEffect(PhysicalEntity caster) {
		return self;
	}

	@Override
	public void cast(AdventureScreen screen, PhysicalEntity caster) {
	}

}
