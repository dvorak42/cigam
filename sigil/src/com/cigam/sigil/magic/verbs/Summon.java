package com.cigam.sigil.magic.verbs;

import java.util.ArrayList;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.cigam.sigil.Constants;
import com.cigam.sigil.Utils;
import com.cigam.sigil.magic.Spell;
import com.cigam.sigil.magic.SpellDescriptor;
import com.cigam.sigil.materials.Summoning;


public class Summon extends Spell {
	private SpellDescriptor toSummonTo;
	private ArrayList<SpellDescriptor> summonCriteria;

	
	public Summon(){
		super();
		defaultDuration = Constants.SPELL_DEFAULT_DURATION;
		defaultRadius = Constants.SPELL_LONG_RANGE;
		summonCriteria = new ArrayList<SpellDescriptor>();
		area.set(Utils.initSpellHitBox(defaultRadius, Constants.SPELL_SCALE_FACTOR));
		//area.setRadius(defaultRadius/Constants.SPELL_SCALE_FACTOR);
		effectValue = Constants.FORCE_MEDIUM;
		argsNum = 4;
		this.gui = Utils.makeVerbGui(Utils.classesToIconPaths.get(this.getClass()));
	}

	@Override
	public SpellDescriptor evalEffect() {
		toSummonTo = target.evalEffect();
		summonCriteria.clear();
		for(Spell s: arguments){
			summonCriteria.add(s.evalEffect());
		}
		//System.out.println(caster);
		SpellDescriptor effect = new SpellDescriptor(new Summoning(toSummonTo, summonCriteria, effectValue), defaultDuration, defaultRadius, effectValue, toSummonTo, summonCriteria, caster.body.getAngle(), area, caster.body.getWorldCenter());
		return effect;
	}
	@Override
	public void cast() {
	//System.out.println(target.bd.position + " is caster location");
	//System.out.println(bd.position.add(castDir.mul(this.fd.shape.m_radius)) + " is created object location");
	screen.createSpellEffect(evalEffect());
	}
}
