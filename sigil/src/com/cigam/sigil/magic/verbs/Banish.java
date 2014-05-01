package com.cigam.sigil.magic.verbs;

import java.util.ArrayList;

import com.cigam.sigil.Constants;
import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.Utils;
import com.cigam.sigil.magic.Spell;
import com.cigam.sigil.magic.SpellDescriptor;
import com.cigam.sigil.materials.Banishment;
import com.cigam.sigil.screens.AdventureScreen;



public class Banish extends Spell {
	private SpellDescriptor toSummonTo;
	private ArrayList<SpellDescriptor> summonCriteria;
	public Banish(){
		super();
		defaultDuration = Constants.SPELL_DEFAULT_DURATION;
		defaultRadius = Constants.SPELL_LONG_RANGE;
		summonCriteria = new ArrayList<SpellDescriptor>();
		area.set(Utils.initSpellHitBox(defaultRadius, Constants.SPELL_SCALE_FACTOR));
		effectValue = -Constants.FORCE_MEDIUM;
		argsNum = 4;
		type = Spell.Type.VERB;
		this.gui = Utils.makeVerbGui(Utils.classesToIconPaths.get(this.getClass()));


	}
	/*
	public Banish(Spell target,  ArrayList<Spell> args) {
		super(target, args);
		summonCriteria = new ArrayList<SpellDescriptor>();
		area = new CircleShape();
		area.setRadius(defaultRadius);
		effectValue = -Constants.FORCE_MEDIUM;
	}
	public Banish(PhysicalEntity c, AdventureScreen b, Target target,  ArrayList<Spell> args) {
		super(c, b, target, args);
		summonCriteria = new ArrayList<SpellDescriptor>();
		area = new CircleShape();
		area.setRadius(defaultRadius);
	}*/

	@Override
	public SpellDescriptor evalEffect(PhysicalEntity caster) {
		toSummonTo = target.evalEffect(caster);
		summonCriteria.clear();
		for(Spell s: arguments){
			summonCriteria.add(s.evalEffect(caster));
		}
		//System.out.println(caster);
		SpellDescriptor effect = new SpellDescriptor(new Banishment(toSummonTo, summonCriteria, effectValue), defaultDuration, defaultRadius, effectValue, toSummonTo, summonCriteria, caster.body.getAngle(), area, caster.body.getWorldCenter());
		return effect;
	}
	@Override
	public void cast(AdventureScreen screen, PhysicalEntity caster) {
	//System.out.println(target.bd.position + " is caster location");
	//System.out.println(bd.position.add(castDir.mul(this.fd.shape.m_radius)) + " is created object location");
	screen.createSpellEffect(evalEffect(caster));
	}
}
