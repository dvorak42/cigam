package com.cigam.sigil.materials;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import com.cigam.sigil.MaterialDescriptor;
import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.magic.Area;
import com.cigam.sigil.magic.SpellEffect;
import com.cigam.sigil.screens.BattleScreen;

public class Backgroundium extends MaterialDescriptor {

	public Backgroundium() {
		super();
		this.init(null, 0, 0, 0);
	}

}
