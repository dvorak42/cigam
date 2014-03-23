package com.cigam.sigil.magic;

import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import com.cigam.sigil.PhysicalEntity;

public class SpellEffect extends PhysicalEntity {
	public float duration;

	
	public SpellEffect(float duration, World world, MaterialDescriptor material, BodyDef bd, FixtureDef[] fds) {
		super(world, material, bd, fds);
		this.duration = duration;
		bd.userData = this;
	}

}
