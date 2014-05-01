package com.cigam.sigil.magic.targets;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.cigam.sigil.Utils;
import com.cigam.sigil.magic.Spell;
import com.cigam.sigil.magic.SpellDescriptor;
import com.cigam.sigil.materials.SelfMat;

public class Self extends Spell {
	private SpellDescriptor self;
	public Self(){
		CircleShape c = new CircleShape();
		c.setRadius(10);
		self = new SpellDescriptor(new SelfMat(), 10, 1,0, null, null, 0, c, Vector2.Zero);
		this.gui = Utils.makeRuneGui(Utils.classesToIconPaths.get(this.getClass()));
	}
	


	@Override
	public SpellDescriptor evalEffect() {
		return self;
	}

	@Override
	public void cast() {
	}

}
