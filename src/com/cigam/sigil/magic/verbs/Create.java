package com.cigam.sigil.magic.verbs;

import java.util.ArrayList;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.FixtureDef;
import com.cigam.sigil.CigamGame;
import com.cigam.sigil.Constants.Direction;
import com.cigam.sigil.Helper;
import com.cigam.sigil.MaterialDescriptor;
import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.magic.*;
import com.cigam.sigil.materials.Creation;
import com.cigam.sigil.screens.*;


public class Create extends Verb {
	private SpellDescriptor toCreate;
	private float defaultDuration = 10;
	private float defaultRadius = 50;
	
	public Create(Verb target, ArrayList<Spell> args) {
		super(target, args);
	}
	public Create(PhysicalEntity c,  BattleScreen b, Target target, ArrayList<Spell> args) {
		super(c, b, target, args);
	}
	
	@Override
	public void topEvalEffect(){
		toCreate = target.evalEffect();
	}

	@Override
	public SpellDescriptor evalEffect() {
		toCreate = target.evalEffect();
		CircleShape c = new CircleShape();
		c.setRadius(defaultRadius);
		Vec2 pos = caster.body.getPosition().add(Helper.v2v(Helper.directionToVector(caster.direction)));
		SpellDescriptor effect = new SpellDescriptor(new Creation(), toCreate.duration*defaultDuration, toCreate, null, caster.direction, c, pos);
		return effect;
	}
	@Override
	public void cast() {
		Vec2 castDir = Helper.v2v(Helper.directionToVector(caster.direction));
		castDir.normalize();
		System.out.println(toCreate);
		toCreate.position = caster.body.getPosition();
		//System.out.println(target.bd.position + " is caster location");
		//System.out.println(bd.position.add(castDir.mul(this.fd.shape.m_radius)) + " is created object location");
		screen.createSpellEffect(evalEffect());
	}
}
