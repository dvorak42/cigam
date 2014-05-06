package com.cigam.sigil.magic.targets;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.magic.Spell;
import com.cigam.sigil.magic.SpellDescriptor;
import com.cigam.sigil.materials.EmptyMat;
import com.cigam.sigil.screens.AdventureScreen;

public class Empty extends Spell {
	private SpellDescriptor mat;
	public Empty(){
		CircleShape c = new CircleShape();
		c.setRadius(10);
		mat = new SpellDescriptor(new EmptyMat(), 10, 1, 0, null, null, 0, c, Vector2.Zero);
		type = Spell.Type.TARGET;
	}
	
	@Override
	public SpellDescriptor evalEffect(PhysicalEntity caster) {
		return mat;
	}

	@Override
	public void cast(AdventureScreen screen, PhysicalEntity caster) {}

	@Override
	public Pixmap spellDiagram() {
		return new Pixmap(0, 0, Format.RGB888);
	}
}
