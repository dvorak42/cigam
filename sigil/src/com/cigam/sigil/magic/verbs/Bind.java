package com.cigam.sigil.magic.verbs;

import java.util.ArrayList;

import com.badlogic.gdx.physics.box2d.CircleShape;
import com.cigam.sigil.Constants;
import com.cigam.sigil.Utils;
import com.cigam.sigil.magic.Spell;
import com.cigam.sigil.magic.SpellDescriptor;
import com.cigam.sigil.materials.Binding;

public class Bind extends Spell {
	private ArrayList<SpellDescriptor> toBeBound;
	private SpellDescriptor toBindInto;
	private float defaultDuration = Constants.SPELL_DEFAULT_DURATION;
	private float defaultRadius = Constants.SPELL_SHORT_RANGE;
	
	public Bind(){
		super();
		toBeBound = new ArrayList<SpellDescriptor>();
		area.set(Utils.initSpellHitBox(defaultRadius, Constants.SPELL_SCALE_FACTOR));
		area.setRadius(defaultRadius*Constants.SPELL_SCALE_FACTOR);
		effectValue = Constants.BIND_EFFECT_VALUE;
		argsNum = 4;
		this.gui = Utils.makeVerbGui(Utils.classesToIconPaths.get(this.getClass()));

	}

	@Override
	public void cast() {
		screen.createSpellEffect(evalEffect());
	}

	@Override
	public SpellDescriptor evalEffect() {
		toBindInto = target.evalEffect();
		toBeBound.clear();
		for(Spell s: arguments){
			toBeBound.add(s.evalEffect());
		}
		SpellDescriptor effect = new SpellDescriptor(new Binding(toBindInto, toBeBound, effectValue), defaultDuration, effectValue, toBindInto, toBeBound, caster.body.getAngle(), area, caster.body.getWorldCenter());
		return effect;
	}

}
