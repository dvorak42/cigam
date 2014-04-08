package com.cigam.sigil.magic.verbs;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
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
	public Create(PhysicalEntity c,  AdventureScreen b, Target target, ArrayList<Spell> args) {
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
		System.out.println(toCreate.duration);
		Vector2 pos = caster.body.getWorldCenter().cpy().rotate(caster.body.getAngle());
		SpellDescriptor effect = new SpellDescriptor(new Creation(), defaultDuration, toCreate, null, caster.body.getAngle(), c, pos);
		return effect;
	}
	@Override
	public void cast() {
		Vector2 castDir = new Vector2(1, 0).rotate(caster.body.getAngle());
		castDir.nor();
		//System.out.println(toCreate);
		toCreate.position = caster.body.getWorldCenter();
		//System.out.println(target.bd.position + " is caster location");
		//System.out.println(bd.position.add(castDir.mul(this.fd.shape.m_radius)) + " is created object location");
		screen.createSpellEffect(evalEffect());
	}
}
