package com.cigam.sigil.magic;

import java.util.ArrayList;

import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import com.cigam.sigil.MaterialDescriptor;
import com.cigam.sigil.PhysicalEntity;

public class SpellEffect extends PhysicalEntity {
	public float duration;
	public SpellDescriptor target;
	public ArrayList<SpellDescriptor> arguments;

	public SpellEffect(World world, SpellDescriptor s){
		super(world,s);
		this.duration = s.duration;
		this.body.setUserData(this);
		this.target = s.target;
		this.arguments = s.arguments;
	}

}
