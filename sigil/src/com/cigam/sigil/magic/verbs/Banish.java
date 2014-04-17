package com.cigam.sigil.magic.verbs;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.cigam.sigil.Constants;
import com.cigam.sigil.Utils;
import com.cigam.sigil.magic.*;
import com.cigam.sigil.materials.Banishment;



public class Banish extends Spell {
	private SpellDescriptor toSummonTo;
	private ArrayList<SpellDescriptor> summonCriteria;
	private float defaultDuration = Constants.SPELL_DEFAULT_DURATION;
	private float defaultRadius = Constants.SPELL_LONG_RANGE;
	public Banish(){
		super();
		summonCriteria = new ArrayList<SpellDescriptor>();
		area = new CircleShape();
		area.setRadius(defaultRadius);
		effectValue = -Constants.FORCE_MEDIUM;
		argsNum = 4;
		gui = Utils.makeVerbGui("UI/banish512.png");
		
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
	public SpellDescriptor evalEffect() {
		toSummonTo = target.evalEffect();
		for(Spell s: arguments){
			summonCriteria.add(s.evalEffect());
		}
		System.out.println(caster);
		SpellDescriptor effect = new SpellDescriptor(new Banishment(toSummonTo, summonCriteria, effectValue), defaultDuration, effectValue, toSummonTo, summonCriteria, caster.body.getAngle(), area, caster.body.getWorldCenter());
		return effect;
	}
	@Override
	public void cast() {
	//System.out.println(target.bd.position + " is caster location");
	//System.out.println(bd.position.add(castDir.mul(this.fd.shape.m_radius)) + " is created object location");
	screen.createSpellEffect(evalEffect());
	}
}
