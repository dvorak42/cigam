package com.cigam.sigil.magic.verbs;

import java.util.ArrayList;

import org.jbox2d.collision.AABB;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.FixtureDef;
import com.cigam.sigil.CigamGame;
import com.cigam.sigil.ClosestQueryCallback;
import com.cigam.sigil.Helper;
import com.cigam.sigil.MaterialDescriptor;
import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.magic.*;
import com.cigam.sigil.materials.Creation;
import com.cigam.sigil.materials.Summoning;
import com.cigam.sigil.screens.*;


public class Summon extends Verb {
	private SpellDescriptor toSummonTo;
	private ArrayList<SpellDescriptor> summonCriteria;
	private float defaultDuration = 10;
	private float defaultRadius = 300;
	
	public Summon(Verb target, ArrayList<Spell> args) {
		super(target, args);
		summonCriteria = new ArrayList<SpellDescriptor>();
		area = new CircleShape();
		area.setRadius(defaultRadius);
	}
	public Summon(PhysicalEntity c, BattleScreen b, Target target, ArrayList<Spell> args) {
		super(c, b, target, args);
		summonCriteria = new ArrayList<SpellDescriptor>();
		area = new CircleShape();
		area.setRadius(defaultRadius);
	}
	
	@Override
	public void topEvalEffect(){
		toSummonTo = target.evalEffect();
		//TODO: SummonCriteria;
	}

	@Override
	public SpellDescriptor evalEffect() {
		toSummonTo = target.evalEffect();
		//TODO: summonCriteria
		System.out.println(caster);
		SpellDescriptor effect = new SpellDescriptor(new Summoning(), defaultDuration, toSummonTo, summonCriteria, caster.direction, area, caster.body.getPosition());
		return effect;
	}
	@Override
	public void cast() {
	//System.out.println(target.bd.position + " is caster location");
	//System.out.println(bd.position.add(castDir.mul(this.fd.shape.m_radius)) + " is created object location");
	screen.createSpellEffect(evalEffect());
	}
}
