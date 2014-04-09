package com.cigam.sigil.magic.verbs;

import java.util.ArrayList;

import com.badlogic.gdx.physics.box2d.CircleShape;
import com.cigam.sigil.Constants;
import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.magic.Spell;
import com.cigam.sigil.magic.SpellDescriptor;
import com.cigam.sigil.materials.Binding;
import com.cigam.sigil.screens.AdventureScreen;

public class Bind extends Spell {
	private ArrayList<SpellDescriptor> toBeBound;
	private SpellDescriptor toBindInto;
	private float defaultDuration = Constants.SPELL_DEFAULT_DURATION;
	private float defaultRadius = Constants.SPELL_SHORT_RANGE;
	
	public Bind(){
		super();
		toBeBound = new ArrayList<SpellDescriptor>();
		area = new CircleShape();
		area.setRadius(defaultRadius);
		effectValue = Constants.BIND_EFFECT_VALUE;
		argsNum = 4;
	}
	/*
	public Bind(Spell target, ArrayList<Spell> args) {
		super(target, args);
		toBeBound = new ArrayList<SpellDescriptor>();
		area = new CircleShape();
		area.setRadius(defaultRadius);
		effectValue = Constants.BIND_EFFECT_VALUE;
	}
	
	public Bind(PhysicalEntity caster, AdventureScreen b, Target target, ArrayList<Spell> args) {
		super(caster, b, target, args);
		toBeBound = new ArrayList<SpellDescriptor>();
		area = new CircleShape();
		area.setRadius(defaultRadius);
	}*/

	@Override
	public void cast() {
		screen.createSpellEffect(evalEffect());
	}

	@Override
	public SpellDescriptor evalEffect() {
		toBindInto = target.evalEffect();
		for(Spell s: arguments){
			toBeBound.add(s.evalEffect());
		}
		SpellDescriptor effect = new SpellDescriptor(new Binding(toBindInto, toBeBound, effectValue), defaultDuration, effectValue, toBindInto, toBeBound, caster.body.getAngle(), area, caster.body.getWorldCenter());
		return effect;
	}

}
