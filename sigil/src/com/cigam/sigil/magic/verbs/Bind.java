package com.cigam.sigil.magic.verbs;

import java.util.ArrayList;

import com.cigam.sigil.Constants;
import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.Utils;
import com.cigam.sigil.magic.Spell;
import com.cigam.sigil.magic.SpellDescriptor;
import com.cigam.sigil.materials.Binding;
import com.cigam.sigil.screens.AdventureScreen;

public class Bind extends Spell {
	private ArrayList<SpellDescriptor> toBeBound;
	private SpellDescriptor toBindInto;
	
	
	public Bind(){
		super();
		defaultDuration = Constants.SPELL_DEFAULT_DURATION;
		defaultRadius = Constants.SPELL_MEDIUM_RANGE;
		toBeBound = new ArrayList<SpellDescriptor>();
		area.set(Utils.initSpellHitBox(defaultRadius, Constants.SPELL_SCALE_FACTOR));
		effectValue = Constants.BIND_EFFECT_VALUE;
		type = Spell.Type.VERB;
		this.gui = Utils.makeVerbGui(Utils.classesToIconPaths.get(this.getClass()));

	}

	@Override
	public void cast(AdventureScreen screen, PhysicalEntity caster) {
		screen.createSpellEffect(evalEffect(caster));
	}

	@Override
	public SpellDescriptor evalEffect(PhysicalEntity caster) {
		toBindInto = target.evalEffect(caster);
		toBeBound.clear();
		for(Spell s: arguments){
			toBeBound.add(s.evalEffect(caster));
		}
		SpellDescriptor effect = new SpellDescriptor(new Binding(toBindInto, toBeBound, effectValue), defaultDuration, defaultRadius, effectValue, toBindInto, toBeBound, caster.body.getAngle(), area, caster.body.getWorldCenter());
		return effect;
	}

}
