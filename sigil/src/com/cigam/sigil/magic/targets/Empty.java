package com.cigam.sigil.magic.targets;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.cigam.sigil.magic.Spell;
import com.cigam.sigil.magic.SpellDescriptor;
import com.cigam.sigil.materials.EmptyMat;
import com.cigam.sigil.materials.SelfMat;

public class Empty extends Spell {
	private SpellDescriptor mat;
	public Empty(){
		CircleShape c = new CircleShape();
		c.setRadius(10);
		mat = new SpellDescriptor(new EmptyMat(), 10, 1, null, null, 0, c, Vector2.Zero);
	}
	
	@Override
	public SpellDescriptor evalEffect() {
		return mat;
	}

	@Override
	public void cast() {}

}